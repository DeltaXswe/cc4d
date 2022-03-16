package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.Characteristic;

import java.util.Optional;

public interface FindCharacteristicPort {
    Optional<Characteristic> find(int deviceId, int id);
}
