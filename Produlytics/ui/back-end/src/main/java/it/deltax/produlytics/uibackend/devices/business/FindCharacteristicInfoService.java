package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.devices.business.ports.in.FindCharacteristicInfoUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindDevicePort;

import java.util.Optional;

public class FindCharacteristicInfoService implements FindCharacteristicInfoUseCase {

    private final FindCharacteristicPort findCharacteristicPort;
    private final FindDevicePort findDevicePort;

    public FindCharacteristicInfoService(
        FindCharacteristicPort findCharacteristicPort,
        FindDevicePort findDevicePort
    ) {
        this.findCharacteristicPort = findCharacteristicPort;
        this.findDevicePort = findDevicePort;
    }

    @Override
    public Optional<CharacteristicDisplayInfo> find(int machineId, int id) {
        return findCharacteristicPort.find(machineId, id)
            .flatMap(characteristic -> findDevicePort.find(machineId)
                .map(machine ->
                    new CharacteristicDisplayInfo(machine, characteristic)
                )
            );
    }
}
