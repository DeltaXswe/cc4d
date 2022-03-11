package it.deltax.produlytics.uibackend.detection.business.ports.out;

import it.deltax.produlytics.uibackend.detection.business.domain.DetectionLight;

import java.util.List;
import java.util.Optional;

public interface ListDetectionsByCharacteristicPort {
    List<DetectionLight> listByCharacteristic(int machineId, int characteristicId, Optional<Long> lastUtc);
}
