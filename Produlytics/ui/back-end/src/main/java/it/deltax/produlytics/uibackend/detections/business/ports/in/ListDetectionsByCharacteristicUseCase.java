package it.deltax.produlytics.uibackend.detections.business.ports.in;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionLight;

import java.util.List;
import java.util.Optional;

public interface ListDetectionsByCharacteristicUseCase {
    List<DetectionLight> listByCharacteristic(int deviceId, int characteristicId, Long createdAfter);
}
