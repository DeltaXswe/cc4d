package it.deltax.uibackend.business.ports.out;

import it.deltax.uibackend.business.domain.CharacteristicInfo;

import java.util.Optional;

public interface FindCharacteristicInfoPort {
    Optional<CharacteristicInfo> find(long machine, String characteristicName);
}
