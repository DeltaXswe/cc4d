package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionSerie;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class CachedDetectionSerie implements DetectionSerie {
	private final FindLastDetectionsPort findLastDetectionsPort;
	private final InsertDetectionPort insertDetectionPort;
	private final MarkOutlierPort markOutlierPort;

	private final List<ControlChart> controlCharts;

	private final int deviceId;
	private final int characteristicId;

	private final Deque<CachedDetection> lastCachedDetections;

	CachedDetectionSerie(
		FindLastDetectionsPort findLastDetectionsPort,
		InsertDetectionPort insertDetectionPort,
		MarkOutlierPort markOutlierPort,
		List<ControlChart> controlCharts,
		int deviceId,
		int characteristicId
	) {
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.insertDetectionPort = insertDetectionPort;
		this.markOutlierPort = markOutlierPort;
		this.controlCharts = controlCharts;
		this.deviceId = deviceId;
		this.characteristicId = characteristicId;
		this.lastCachedDetections = new ArrayDeque<>();

		this.fetchLastDetections(15);
	}

	private List<Detection> lastDetections() {
		return this.lastCachedDetections.stream().map(CachedDetection::toDetection).toList();
	}

	private void enqueueDetection(Detection detection) {
		this.lastCachedDetections.addLast(new CachedDetection(detection));
	}

	private void fetchLastDetections(int count) {
		if(this.lastCachedDetections.size() > count) {
			while(this.lastCachedDetections.size() > count) {
				this.lastCachedDetections.removeFirst();
			}
		} else {
			List<Detection> lastDetections = this.findLastDetectionsPort.findLastDetections(this.deviceId,
				this.characteristicId,
				count
			);
			for(Detection detection : lastDetections) {
				this.enqueueDetection(detection);
			}
		}
	}

	public void updateSampleSize(int sampleSize) {
		this.fetchLastDetections(Math.max(15, sampleSize));
		// TODO: Aggiorna calcolatore limiti
	}

	@Override
	public void insertDetection(RawDetection rawDetection) {
		if(this.lastCachedDetections.size() == 15) {
			this.lastCachedDetections.removeFirst();
		}

		Detection detection = new Detection(rawDetection.deviceId(),
			rawDetection.characteristicId(),
			rawDetection.creationTime(),
			rawDetection.value(),
			false
		);

		this.enqueueDetection(detection);
		this.insertDetectionPort.insertDetection(detection);

		List<Detection> lastDetectionWindow = this.lastDetections();
		for(ControlChart controlChart : this.controlCharts) {
			controlChart.analyzeDetection(lastDetectionWindow, new CachedDetectionSerieMarkOutlierAdapter(this));
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
