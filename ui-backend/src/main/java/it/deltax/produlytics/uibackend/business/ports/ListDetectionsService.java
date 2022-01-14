package it.deltax.produlytics.uibackend.business.ports;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListDetectionsUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListDetectionsPort;

import java.util.List;
import java.util.Optional;

public class ListDetectionsService implements ListDetectionsUseCase {

    ListDetectionsPort port;

    public ListDetectionsService(ListDetectionsPort port) {
        this.port = port;
    }

    @Override
    public List<DetectionLight> listDetections(long machine, String characteristic, Optional<Long> createdAfter) {
        return port.listDetections(machine, characteristic, createdAfter);
    }
}
