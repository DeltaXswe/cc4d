package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;

import java.util.List;

public interface GetUnarchivedCharacteristicsUseCase {
    List<CharacteristicTitle> getByDevice(int deviceId);
}
