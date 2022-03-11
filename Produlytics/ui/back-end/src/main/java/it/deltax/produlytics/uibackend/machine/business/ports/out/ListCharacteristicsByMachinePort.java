package it.deltax.produlytics.uibackend.machine.business.ports.out;

import it.deltax.produlytics.uibackend.machine.business.domain.CharacteristicLight;

import java.util.List;

public interface ListCharacteristicsByMachinePort {
    List<CharacteristicLight> listByMachine(int machineId);
}