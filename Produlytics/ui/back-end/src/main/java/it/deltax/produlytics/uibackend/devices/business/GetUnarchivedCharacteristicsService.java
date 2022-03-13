package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicPort;

import java.util.List;

public class GetUnarchivedCharacteristicsService implements GetUnarchivedCharacteristicsUseCase {
    private final FindAllUnarchivedCharacteristicPort port;

    public GetUnarchivedCharacteristicsService(FindAllUnarchivedCharacteristicPort port) {
        this.port = port;
    }

    @Override
    public List<CharacteristicTitle> getByDevice(int deviceId) {
        return port.findAllByDeviceId(deviceId);
    }
}
