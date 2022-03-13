package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.devices.business.ports.in.ListCharacteristicsByDeviceUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.ListCharacteristicsByDevicePort;

import java.util.List;

public class ListCharacteristicsByDeviceService implements ListCharacteristicsByDeviceUseCase {

    private final ListCharacteristicsByDevicePort port;

    public ListCharacteristicsByDeviceService(ListCharacteristicsByDevicePort port) {
        this.port = port;
    }

    @Override
    public List<CharacteristicLight> listByMachine(int machineId) {
        return port.listByMachine(machineId);
    }
}

