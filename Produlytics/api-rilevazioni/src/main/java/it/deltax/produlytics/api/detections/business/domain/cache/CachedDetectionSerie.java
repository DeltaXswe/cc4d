package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.LimitsInfo;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.control_chart.Limits;
import it.deltax.produlytics.api.detections.business.domain.control_chart.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.LimitsCalculator;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.List;
import java.util.Optional;

// Implementazione di riferimento di `DetectionSerie` che si occupa di
// tenere traccia delle ultime `maxCacheSize()` rilevazioni in aggiunta a
// memorizzare quelle nuove e identificare anomalie.
public class CachedDetectionSerie implements DetectionSerie {
	private static final int CONTROL_CHART_DETECTIONS = 15;

	private final InsertDetectionPort insertDetectionPort;

	private final LimitsCalculator limitsCalculator;
	private final List<ControlChart> controlCharts;

	private final int deviceId;
	private final int characteristicId;

	private final Cache cache;

	// Dimensione del campione per l'auto-adjust, se attivo.
	private Optional<Integer> sampleSize;

	CachedDetectionSerie(
		FindLastDetectionsPort findLastDetectionsPort,
		InsertDetectionPort insertDetectionPort,
		MarkOutlierPort markOutlierPort,
		LimitsCalculator limitsCalculator,
		List<ControlChart> controlCharts,
		int deviceId,
		int characteristicId
	) {
		this.insertDetectionPort = insertDetectionPort;
		this.limitsCalculator = limitsCalculator;
		this.controlCharts = controlCharts;
		this.deviceId = deviceId;
		this.characteristicId = characteristicId;
		this.cache = new Cache(findLastDetectionsPort, markOutlierPort, deviceId, characteristicId);
		this.sampleSize = Optional.empty();
		this.cache.updateSize(this.maxCacheSize());
	}

	private int maxCacheSize() {
		return Math.max(CONTROL_CHART_DETECTIONS, this.sampleSize.orElse(0));
	}

	@Override
	public void insertDetection(RawDetection rawDetection) {
		Detection newDetection = new Detection(
			rawDetection.deviceId(),
			rawDetection.characteristicId(),
			rawDetection.creationTime(),
			rawDetection.value(),
			false
		);

		this.updateLimits(rawDetection.limitsInfo(), newDetection);

		this.cache.insertDetection(newDetection);
		this.insertDetectionPort.insertDetection(newDetection);

		this.updateControlCharts(rawDetection.limitsInfo());
	}

	private void updateLimits(LimitsInfo limitsInfo, Detection newDetection) {
		// Aggiorna la grandezza del campione
		Optional<Integer> newSampleSize = limitsInfo.sampleSize();
		if(newSampleSize != this.sampleSize) {
			this.sampleSize = newSampleSize;
			this.cache.updateSize(this.maxCacheSize());
			newSampleSize.ifPresent(size -> {
				List<Double> lastValues = this.cache.lastNDetections(size).map(MarkableDetection::getValue).toList();
				this.limitsCalculator.reset(lastValues);
			});
		}

		// Aggiorna il calcolatore dei limiti
		this.sampleSize.ifPresent(sample -> {
			Optional<MarkableDetection> oldDetection = this.cache.lastNDetections(sample).findFirst();
			if(oldDetection.isPresent()) {
				this.limitsCalculator.slide(oldDetection.get().getValue(), newDetection.value());
			} else {
				this.limitsCalculator.add(newDetection.value());
			}
		});
	}

	private void updateControlCharts(LimitsInfo limitsInfo) {
		Limits limits = new Limits(
			this.sampleSize.map(_sampleSize -> this.limitsCalculator.getCalculatedLimits()),
			limitsInfo.lowerLimit(),
			limitsInfo.upperLimit(),
			limitsInfo.mean()
		);
		List<MarkableDetection> lastDetections = this.cache.lastNDetections(CONTROL_CHART_DETECTIONS).toList();
		for(ControlChart controlChart : this.controlCharts) {
			controlChart.analyzeDetection(lastDetections, limits);
		}
	}
}
