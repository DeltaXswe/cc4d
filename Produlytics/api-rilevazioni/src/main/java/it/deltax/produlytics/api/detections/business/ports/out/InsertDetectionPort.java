package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.Detection;

public interface InsertDetectionPort {
	void insertDetection(Detection detection);
}
