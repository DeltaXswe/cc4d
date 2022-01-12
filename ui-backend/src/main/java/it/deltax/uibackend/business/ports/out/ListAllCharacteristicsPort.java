package it.deltax.uibackend.business.ports.out;

import it.deltax.uibackend.business.domain.CharacteristicLight;

import java.util.List;

public interface ListAllCharacteristicsPort {
    List<CharacteristicLight> listAllCharacteristics();
}