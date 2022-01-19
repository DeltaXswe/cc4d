package it.deltax.produlytics.api.business.ports.in;

import it.deltax.produlytics.api.business.domain.Detection;
import it.deltax.produlytics.api.business.domain.DetectionLight;


import java.util.Optional;

public interface InsertDetectionUseCase {
    Optional<Detection> insertRilevazione(DetectionLight rilevazione);
}

