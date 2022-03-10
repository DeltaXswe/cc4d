package it.deltax.produlytics.api.detections.business.domain.queue;

import it.deltax.produlytics.api.detections.business.domain.RawDetection;

public interface DetectionQueue {
	void enqueueDetection(RawDetection detection);
}
