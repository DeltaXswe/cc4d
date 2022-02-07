package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;

import java.util.List;

public interface ListCharacteristicsByMachineUseCase {
    List<CharacteristicLight> listByMachine(long machineId);
}
