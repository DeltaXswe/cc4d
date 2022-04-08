package it.deltax.produlytics.uibackend.detections.business.ports.out;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;

import java.util.List;
import java.util.OptionalLong;

public interface FindAllDetectionsPort {
	List<Detection> findAllByCharacteristic(int deviceId, int characteristicId, OptionalLong olderThan);
}
