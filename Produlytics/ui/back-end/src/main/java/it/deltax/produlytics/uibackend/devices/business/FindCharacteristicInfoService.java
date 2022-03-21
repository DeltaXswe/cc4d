package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.devices.business.ports.in.FindCharacteristicInfoUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindDevicePort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
    public Optional<CharacteristicDisplayInfo> find(int deviceId, int id) {
        return findCharacteristicPort.find(deviceId, id)
            .flatMap(characteristic -> findDevicePort.find(deviceId)
                .map(device ->
                    new CharacteristicDisplayInfo(device, characteristic)
                )
            );
    }
}
