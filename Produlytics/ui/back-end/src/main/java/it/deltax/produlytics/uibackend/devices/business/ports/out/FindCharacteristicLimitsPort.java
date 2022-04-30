package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import java.util.Optional;

/** La porta per l'ottenimento dei limiti di una caratteristica di una macchina */
public interface FindCharacteristicLimitsPort {
  Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId);
}
