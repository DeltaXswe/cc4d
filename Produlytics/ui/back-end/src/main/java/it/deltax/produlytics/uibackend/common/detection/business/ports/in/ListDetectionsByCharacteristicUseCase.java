package it.deltax.produlytics.uibackend.common.detection.business.ports.in;

import it.deltax.produlytics.uibackend.common.detection.business.domain.DetectionLight;

import java.util.List;
import java.util.Optional;

public interface ListDetectionsByCharacteristicUseCase {
    List<DetectionLight> listByCharacteristic(int machineId, int characteristicId, Optional<Long> createdAfter);
}
