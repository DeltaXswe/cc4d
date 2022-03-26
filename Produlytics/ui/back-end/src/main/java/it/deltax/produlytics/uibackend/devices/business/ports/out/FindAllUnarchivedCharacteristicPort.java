package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;

import java.util.List;
import java.util.Optional;

public interface FindAllUnarchivedCharacteristicPort {
    Optional<List<CharacteristicTitle>> findAllByDeviceId(int deviceId);
}