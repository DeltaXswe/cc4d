package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.LimitsInfo;
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
	private Optional<Integer> sampleSize;

	private final Deque<CachedDetection> lastCachedDetections;

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

		this.lastCachedDetections = new ArrayDeque<>();
	}

	private List<Detection> lastDetections() {
		return this.lastCachedDetections.stream().map(CachedDetection::toDetection).toList();
	}

	private void enqueueDetection(Detection detection) {
		this.lastCachedDetections.addLast(new CachedDetection(detection));
	}

	private int maxQueueSize() {
		return Math.max(CONTROL_CHART_MIN_DETECTIONS, this.sampleSize.orElse(0));
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

		this.slideDetectionsWindow(newDetection);

		this.insertDetectionPort.insertDetection(newDetection);

		this.updateControlCharts(rawDetection.limitsInfo());
	}

	private void updateLimits(LimitsInfo limitsInfo, Detection newDetection) {
		if(limitsInfo.sampleSize() != this.sampleSize) {
			this.sampleSize = limitsInfo.sampleSize();
			// TODO: Se queue troppo lunga, tronca
			// TODO: Se queue troppo corta, fetch
			// TODO: Re-inizializza limitsCalculator con nuova queue (opzionalmente troncata)
		} else {
			// TODO: Aggiornare limitsCalculator
		}
	}

	private void slideDetectionsWindow(Detection newDetection) {
		if(this.lastCachedDetections.size() == this.maxQueueSize()) {
			this.lastCachedDetections.removeFirst();
		}
		this.enqueueDetection(newDetection);
	}

	private void updateControlCharts(LimitsInfo limitsInfo) {
		Limits limits = new Limits(
			this.sampleSize.map(_sampleSize -> this.limitsCalculator.getCalculatedLimits()),
			limitsInfo.lowerLimit(),
			limitsInfo.upperLimit(),
			limitsInfo.mean()
		);
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
