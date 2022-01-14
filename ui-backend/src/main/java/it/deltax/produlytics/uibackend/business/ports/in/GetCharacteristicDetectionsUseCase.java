package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicDetections;

import java.util.Optional;

public interface GetCharacteristicDetectionsUseCase {

    Optional<CharacteristicDetections> getCharacteristicDetections(long machine, String characteristic);
}
