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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// Implementazione di riferimento di `DetectionSerie` che si occupa di
// tenere traccia delle ultime `maxCacheSize()` rilevazioni in aggiunta a
// memorizzare quelle nuove e identificare anomalie.
public class CachedDetectionSerie implements DetectionSerie {
	private static final int CONTROL_CHART_DETECTIONS = 15;

	private final FindLastDetectionsPort findLastDetectionsPort;
	private final InsertDetectionPort insertDetectionPort;
	private final MarkOutlierPort markOutlierPort;

	private final LimitsCalculator limitsCalculator;
	private final List<ControlChart> controlCharts;

	private final int deviceId;
	private final int characteristicId;
	private final Deque<CachedDetection> cachedDetections;
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
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.insertDetectionPort = insertDetectionPort;
		this.markOutlierPort = markOutlierPort;
		this.limitsCalculator = limitsCalculator;
		this.controlCharts = controlCharts;
		this.deviceId = deviceId;
		this.characteristicId = characteristicId;
		this.sampleSize = Optional.empty();
		this.cachedDetections = new ArrayDeque<>();
		this.fetchLastDetections();
	}

	// Ritorna uno stream delle ultime `count` rilevazioni (o meno, se non sono presenti).
	private Stream<CachedDetection> lastDetectionsStream(int count) {
		int toSkip = this.cachedDetections.size() - Math.min(count, this.cachedDetections.size());
		return this.cachedDetections.stream().skip(toSkip);
	}

	private void enqueueDetection(Detection detection) {
		this.cachedDetections.addLast(new CachedDetection(detection));
	}

	// La dimensione massima della cache prima che inizi a contenere elementi inutili.
	private int maxCacheSize() {
		return Math.max(CONTROL_CHART_DETECTIONS, this.sampleSize.orElse(0));
	}

	// Ripopola `cachedDetections` con le rilevazioni memorizzate.
	private void fetchLastDetections() {
		List<Detection> newLastDetections = this.findLastDetectionsPort.findLastDetections(this.deviceId,
			this.characteristicId,
			this.maxCacheSize()
		);
		newLastDetections.forEach(this::enqueueDetection);
	}

	@Override
	public void insertDetection(RawDetection rawDetection) {
		Detection newDetection = new Detection(rawDetection.deviceId(),
			rawDetection.characteristicId(),
			rawDetection.creationTime(),
			rawDetection.value(),
			false
		);

		// Questo è importante che sia prima della chiamata a `enqueueDetection`
		// perchè assume che gli ultimi `sampleSize` elementi di `cachedDetections`
		// siano quelli con cui sono attualmente calcolati media e deviazione standard.
		this.updateLimits(rawDetection.limitsInfo(), newDetection);

		if(this.cachedDetections.size() == this.maxCacheSize()) {
			this.cachedDetections.removeFirst();
		}
		this.enqueueDetection(newDetection);
		this.insertDetectionPort.insertDetection(newDetection);

		this.updateControlCharts(rawDetection.limitsInfo());
	}

	private void updateLimits(LimitsInfo limitsInfo, Detection newDetection) {
		// Se la grandezza del campione è cambiata:
		// - aggiornala;
		// - aggiorna le rilevazioni in cache visto che potrebbero esserne richieste di più;
		// - riesegui il calcolo di media e deviazione standard (se la nuova grandezza del campione è presente).
		if(limitsInfo.sampleSize() != this.sampleSize) {
			this.sampleSize = limitsInfo.sampleSize();
			this.fetchLastDetections();
			this.sampleSize.ifPresent(sampleSize -> {
				List<Double> newDetectionsValues = this.lastDetectionsStream(sampleSize)
					.map(cachedDetection -> cachedDetection.toDetection().value())
					.toList();
				this.limitsCalculator.reset(newDetectionsValues);
			});
		}

		this.sampleSize.ifPresent(sampleSize -> {
			// Questo è necessario nel caso in cui il numero totale di rilevazioni sia inferiore al numero del campione,
			// e in quel caso la rilevazione va solo aggiunta al calcolatore.
			if(this.cachedDetections.size() >= sampleSize) {
				double oldValue = this.lastDetectionsStream(sampleSize).findFirst().get().toDetection().value();
				this.limitsCalculator.slide(oldValue, newDetection.value());
			} else {
				this.limitsCalculator.add(newDetection.value());
			}
		});
	}

	private void updateControlCharts(LimitsInfo limitsInfo) {
		Limits limits = new Limits(this.sampleSize.map(_sampleSize -> this.limitsCalculator.getCalculatedLimits()),
			limitsInfo.lowerLimit(),
			limitsInfo.upperLimit(),
			limitsInfo.mean()
		);
		List<MarkableDetection> lastDetections = this.lastDetectionsStream(CONTROL_CHART_DETECTIONS)
			.map(cachedDetection -> new MarkableCachedDetectionAdaper(cachedDetection, this.markOutlierPort))
			.map(markableDetection -> (MarkableDetection) markableDetection)
			.toList();
		for(ControlChart controlChart : this.controlCharts) {
			controlChart.analyzeDetection(lastDetections, limits);
		}
	}
}
