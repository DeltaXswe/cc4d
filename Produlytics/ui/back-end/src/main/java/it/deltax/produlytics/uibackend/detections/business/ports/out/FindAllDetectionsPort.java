package it.deltax.produlytics.uibackend.detections.business.ports.out;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;

import java.util.List;

public interface FindAllDetectionsPort {
	List<Detection> findAllByCharacteristic(int deviceId, int characteristicId, DetectionFilters filters);
}
