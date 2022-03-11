package it.deltax.produlytics.api.detections.business.domain.queue;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;

import java.util.List;

public interface DetectionSerie {
	List<Detection> lastDetectionsWindow();
	void insertDetection(RawDetection rawDetection);
	void markOutlier(Detection detection);
}
