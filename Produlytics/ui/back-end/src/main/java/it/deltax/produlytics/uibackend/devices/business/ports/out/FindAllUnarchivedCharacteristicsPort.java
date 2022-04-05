package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;

import java.util.List;

public interface FindAllUnarchivedCharacteristicsPort {
    List<CharacteristicTitle> findAllByDeviceId(int deviceId);
}