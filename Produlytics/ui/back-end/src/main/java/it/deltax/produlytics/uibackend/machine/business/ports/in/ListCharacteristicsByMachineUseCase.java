package it.deltax.produlytics.uibackend.machine.business.ports.in;

import it.deltax.produlytics.uibackend.machine.business.domain.CharacteristicLight;

import java.util.List;

public interface ListCharacteristicsByMachineUseCase {
    List<CharacteristicLight> listByMachine(int machineId);
}
