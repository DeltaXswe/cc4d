package it.deltax.produlytics.uibackend.business.ports.out;

import it.deltax.produlytics.uibackend.business.domain.Characteristic;

import java.util.Optional;

public interface FindCharacteristicPort {
    Optional<Characteristic> find(long machineId, String characteristicCode);
}
