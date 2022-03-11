package it.deltax.produlytics.uibackend.machine.business.ports.out;

import it.deltax.produlytics.uibackend.machine.business.domain.Characteristic;

import java.util.Optional;

public interface FindCharacteristicPort {
    Optional<Characteristic> find(int machineId, int id);
}
