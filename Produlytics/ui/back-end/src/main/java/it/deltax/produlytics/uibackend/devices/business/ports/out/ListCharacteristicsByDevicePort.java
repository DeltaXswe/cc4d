package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLight;

import java.util.List;

public interface ListCharacteristicsByDevicePort {
    List<CharacteristicLight> listByMachine(int machineId);
}