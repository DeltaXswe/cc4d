package it.deltax.produlytics.uibackend.machine.business.ports.in;

import it.deltax.produlytics.uibackend.machine.business.domain.CharacteristicDisplayInfo;

import java.util.Optional;

public interface FindCharacteristicInfoUseCase {
    Optional<CharacteristicDisplayInfo> find(int machineId, int id);
}
