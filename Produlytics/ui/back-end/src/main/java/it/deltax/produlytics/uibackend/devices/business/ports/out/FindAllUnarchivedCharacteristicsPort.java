package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;

public interface FindAllUnarchivedCharacteristicsPort {
    Iterable<CharacteristicTitle> findAllByDeviceId(int deviceId);
}