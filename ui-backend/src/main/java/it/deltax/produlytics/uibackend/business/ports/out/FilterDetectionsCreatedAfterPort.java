package it.deltax.produlytics.uibackend.business.ports.out;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;

import java.util.List;

public interface FilterDetectionsCreatedAfterPort {
    List<DetectionLight> listAllAfter(long machine, String characteristic, long lastUtc);
}
