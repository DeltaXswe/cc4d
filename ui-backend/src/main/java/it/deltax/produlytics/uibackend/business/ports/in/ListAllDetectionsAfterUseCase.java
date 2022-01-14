package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;

import java.util.List;

public interface ListAllDetectionsAfterUseCase {
    List<DetectionLight> listAllDetectionsAfter(long machine, String characteristic, long lastUtc);
}
