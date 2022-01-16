package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicDisplayInfo;

import java.util.Optional;

public interface FindCharacteristicInfoUseCase {
    Optional<CharacteristicDisplayInfo> find(long machineId, String code);
}
