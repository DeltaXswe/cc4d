package it.deltax.produlytics.uibackend.business.ports;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListAllDetectionsAfterUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.FilterDetectionsCreatedAfterPort;

import java.util.List;

public class ListAllDetectionsAfterService implements ListAllDetectionsAfterUseCase {

    FilterDetectionsCreatedAfterPort port;

    public ListAllDetectionsAfterService(FilterDetectionsCreatedAfterPort port) {
        this.port = port;
    }

    @Override
    public List<DetectionLight> listAllDetectionsAfter(long machine, String characteristic, long lastUtc) {
        return port.listAllAfter(machine, characteristic, lastUtc);
    }
}
