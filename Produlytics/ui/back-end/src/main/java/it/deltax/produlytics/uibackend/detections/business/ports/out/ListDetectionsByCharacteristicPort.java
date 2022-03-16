package it.deltax.produlytics.uibackend.detections.business.ports.out;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionLight;

import java.util.List;
import java.util.Optional;

public interface ListDetectionsByCharacteristicPort {
    List<DetectionLight> listByCharacteristic(int deviceId, int characteristicId, Long lastUtc);
}
