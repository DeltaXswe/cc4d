package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;

import java.util.List;

public interface ListAllCharacteristicsUseCase {
    List<CharacteristicLight> listAllCharacteristics();
}
