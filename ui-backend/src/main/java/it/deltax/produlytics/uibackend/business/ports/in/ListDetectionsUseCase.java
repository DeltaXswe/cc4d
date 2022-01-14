package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;

import java.util.List;
import java.util.Optional;

public interface ListDetectionsUseCase {
    List<DetectionLight> listDetections(long machine, String characteristic, Optional<Long> createdAfter);
}
