package it.deltax.produlytics.api.detections.business.domain.queue;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.cache.DetectionCache;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

@SuppressWarnings("ALL")
public class DetectionCacheAdapter implements MarkOutlierPort {
	private final DetectionCache detectionCache;

	DetectionCacheAdapter(DetectionCache detectionCache) {
		this.detectionCache = detectionCache;
	}

	@Override
	public void markOutlier(Detection detection) {
		this.detectionCache.markOutlier(detection);
	}
}
