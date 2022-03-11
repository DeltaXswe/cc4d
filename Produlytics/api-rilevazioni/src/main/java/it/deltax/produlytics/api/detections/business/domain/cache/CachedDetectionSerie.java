package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.control_chart.Limits;
import it.deltax.produlytics.api.detections.business.domain.limits.LimitsCalculator;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class CachedDetectionSerie implements DetectionSerie {
	private static final int CONTROL_CHART_MIN_DETECTIONS = 15;

	private final FindLastDetectionsPort findLastDetectionsPort;
	private final InsertDetectionPort insertDetectionPort;
	private final MarkOutlierPort markOutlierPort;

	private final LimitsCalculator limitsCalculator;
	private final List<ControlChart> controlCharts;

	private final int deviceId;
	private final int characteristicId;

	private final Deque<CachedDetection> lastCachedDetections;
	private int maxQueueSize;

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
		this.lastCachedDetections = new ArrayDeque<>();

		// TODO: supporta calcolatore limiti opzionale
		// TODO: usa sampleSize della caratteristica
		this.updateSampleSize(0);
	}

	private List<Detection> lastDetections() {
		return this.lastCachedDetections.stream().map(CachedDetection::toDetection).toList();
	}

	private void enqueueDetection(Detection detection) {
		this.lastCachedDetections.addLast(new CachedDetection(detection));
	}

	private void fetchLastDetections() {
		if(this.lastCachedDetections.size() > this.maxQueueSize) {
			while(this.lastCachedDetections.size() > this.maxQueueSize) {
				this.lastCachedDetections.removeFirst();
			}
		} else {
			List<Detection> lastDetections = this.findLastDetectionsPort.findLastDetections(this.deviceId,
				this.characteristicId,
				this.maxQueueSize
			);
			for(Detection detection : lastDetections) {
				this.enqueueDetection(detection);
			}
		}
	}

	public void updateSampleSize(int sampleSize) {
		this.maxQueueSize = Math.max(CONTROL_CHART_MIN_DETECTIONS, sampleSize);
		this.fetchLastDetections();
		// TODO: Aggiorna calcolatore limiti
	}

	@Override
	public void insertDetection(RawDetection rawDetection) {
		Detection newDetection = new Detection(rawDetection.deviceId(),
			rawDetection.characteristicId(),
			rawDetection.creationTime(),
			rawDetection.value(),
			false
		);

		if(this.lastCachedDetections.size() == this.maxQueueSize) {
			Detection oldDetection = this.lastCachedDetections.removeFirst().toDetection();
			this.limitsCalculator.update(oldDetection, newDetection);
		} else {
			this.limitsCalculator.update(newDetection);
		}

		this.enqueueDetection(newDetection);
		this.insertDetectionPort.insertDetection(newDetection);

		// TODO: Usa limiti veri
		Limits limits = new Limits(Optional.empty(), Optional.empty());
		List<Detection> lastDetections = this.lastDetections();
		for(ControlChart controlChart : this.controlCharts) {
			controlChart.analyzeDetection(lastDetections, new CachedDetectionSerieMarkOutlierAdapter(this), limits);
		}
	}

	void markOutlier(Detection detection) {
		for(CachedDetection cachedDetection : this.lastCachedDetections) {
			if(cachedDetection.isSameDetection(detection)) {
				cachedDetection.mark(this.markOutlierPort);
				return;
			}
		}

		throw new IllegalStateException("markOutlier called with invalid detection");
	}
}
