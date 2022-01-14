package it.deltax.produlytics.uibackend.business.ports.out;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;

import java.util.List;

public interface DetectionByCharacteristicPort {
    List<DetectionLight> filterByCharacteristic(long machine, String characteristicName);
}
