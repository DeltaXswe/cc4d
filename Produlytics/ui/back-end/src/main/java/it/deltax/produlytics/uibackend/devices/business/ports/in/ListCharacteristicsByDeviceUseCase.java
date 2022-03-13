package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLight;

import java.util.List;

public interface ListCharacteristicsByDeviceUseCase {
    List<CharacteristicLight> listByMachine(int machineId);
}
