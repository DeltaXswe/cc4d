package it.deltax.produlytics.api.business.ports.out;
import java.util.Optional;

import it.deltax.produlytics.api.business.domain.CharacteristicLight;


public interface FindCharacteristcPort{
    Optional<CharacteristicLight> findCharacteristic(long macchina, String name);
}