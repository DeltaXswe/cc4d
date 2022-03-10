package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.Detection;

import java.util.List;

public interface FindLastDetectionsPort {
	List<Detection> findLastDetections(int deviceId, int characteristicId, int count);
}
