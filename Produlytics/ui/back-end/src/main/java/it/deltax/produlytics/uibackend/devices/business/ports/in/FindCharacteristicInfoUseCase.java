package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicDisplayInfo;

import java.util.Optional;

public interface FindCharacteristicInfoUseCase {
    Optional<CharacteristicDisplayInfo> find(int deviceId, int id);
}
