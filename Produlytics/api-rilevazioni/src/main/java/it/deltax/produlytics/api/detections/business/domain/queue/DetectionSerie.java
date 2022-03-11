package it.deltax.produlytics.api.detections.business.domain.queue;

import it.deltax.produlytics.api.detections.business.domain.RawDetection;

public interface DetectionSerie {
	void insertDetection(RawDetection rawDetection);
}
