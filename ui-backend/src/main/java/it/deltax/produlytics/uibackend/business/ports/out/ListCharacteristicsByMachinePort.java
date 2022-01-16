package it.deltax.produlytics.uibackend.business.ports.out;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;

import java.util.List;

public interface ListCharacteristicsByMachinePort {
    List<CharacteristicLight> listByMachine(long machineId);
}