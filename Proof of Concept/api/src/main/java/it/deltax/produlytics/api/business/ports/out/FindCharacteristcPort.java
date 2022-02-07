package it.deltax.produlytics.api.business.ports.out;
import java.util.Optional;

import it.deltax.produlytics.api.business.domain.Characteristic;

public interface FindCharacteristcPort{
    Optional<Characteristic> findCharacteristic(long macchina, String name);
}