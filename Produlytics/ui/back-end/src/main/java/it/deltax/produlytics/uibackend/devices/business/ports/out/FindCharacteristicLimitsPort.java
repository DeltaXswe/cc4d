package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;

import java.util.Optional;

public interface FindCharacteristicLimitsPort {
    Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId);
}
