package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;

import java.util.List;
import java.util.Optional;

public interface ListDetectionsByCharacteristicUseCase {
    List<DetectionLight> listByCharacteristic(long machineId, String characteristicCode, Optional<Long> createdAfter);
}
