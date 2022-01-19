package it.deltax.produlytics.api.business.ports;

import it.deltax.produlytics.api.business.domain.Detection;
import it.deltax.produlytics.api.business.domain.DetectionLight;
import it.deltax.produlytics.api.business.ports.in.InsertDetectionUseCase;
import it.deltax.produlytics.api.business.ports.out.InsertDetectionPort;

import java.util.Optional;

public class InsertDetectionService implements InsertDetectionUseCase {

    private final InsertDetectionPort port;

    public InsertDetectionService(InsertDetectionPort port) {
        this.port = port;
    }

    @Override
    public Optional<Detection> insertRilevazione(DetectionLight rilevazione) {
        return Optional.of(port.insertRilevazione(rilevazione));
    }

}
