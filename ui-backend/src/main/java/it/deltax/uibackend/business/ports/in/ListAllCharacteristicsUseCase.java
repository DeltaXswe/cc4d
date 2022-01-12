package it.deltax.uibackend.business.ports.in;

import it.deltax.uibackend.business.domain.CharacteristicLight;

import java.util.List;

public interface ListAllCharacteristicsUseCase {
    List<CharacteristicLight> listAllCharacteristics();
}
