package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class DetectionCacheImpl implements DetectionCache {
	private final InsertDetectionPort insertDetectionPort;
	private final Queue<CachedDetection> lastDetections;
	private final MarkOutlierPort markOutlierPort;

	DetectionCacheImpl(
		InsertDetectionPort insertDetectionPort, List<Detection> lastDetections, MarkOutlierPort markOutlierPort
	) {
		this.insertDetectionPort = insertDetectionPort;
		this.lastDetections = new ArrayDeque<>(15);
		for(Detection detection : lastDetections) {
			this.enqueueDetection(detection);
		}
		this.markOutlierPort = markOutlierPort;
	}

	@Override
	public List<Detection> findLastDetections() {
		return this.lastDetections.stream().map(CachedDetection::toDetection).toList();
	}

	@Override
	public void insertDetection(RawDetection rawDetection) {
		if(this.lastDetections.size() == 15)
			this.lastDetections.remove();

		Detection detection = new Detection(rawDetection.deviceId(),
			rawDetection.characteristicId(),
			rawDetection.creationTime(),
			rawDetection.value(),
			false
		);

		this.enqueueDetection(detection);
		this.insertDetectionPort.insertDetection(detection);
	}

	private void enqueueDetection(Detection detection) {
		this.lastDetections.add(new CachedDetection(detection));
	}

	@Override
	public void markOutlier(Detection detection) {
		for(CachedDetection cachedDetection : this.lastDetections) {
			if(cachedDetection.isSameDetection(detection)) {
				cachedDetection.mark(this.markOutlierPort);
				return;
			}
		}

		throw new IllegalStateException("markOutlier called with invalid detection");
	}
}
