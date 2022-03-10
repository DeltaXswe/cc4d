package it.deltax.produlytics.uibackend.common.machine.business.ports.out;

import it.deltax.produlytics.uibackend.common.machine.business.domain.CharacteristicLight;

import java.util.List;

public interface ListCharacteristicsByMachinePort {
    List<CharacteristicLight> listByMachine(int machineId);
}