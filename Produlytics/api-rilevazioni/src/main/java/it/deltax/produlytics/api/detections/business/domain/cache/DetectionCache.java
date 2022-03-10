package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;

import java.util.List;

public interface DetectionCache {
	List<Detection> findLastDetections();
	void insertDetection(RawDetection rawDetection);
	void markOutlier(Detection detection);
}
