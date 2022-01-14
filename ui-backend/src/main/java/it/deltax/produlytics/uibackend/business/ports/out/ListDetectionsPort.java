package it.deltax.produlytics.uibackend.business.ports.out;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;

import java.util.List;
import java.util.Optional;

public interface ListDetectionsPort {
    List<DetectionLight> listDetections(long machineId, String characteristicName, Optional<Long> lastUtc);
}
