package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Interfaccia che rappresenta il caso d'uso di ottenimento dei limiti di una caratteristica di una
 * macchina
 */
public interface GetLimitsUseCase {
  CharacteristicLimits getByCharacteristic(int deviceId, int characteristicsId)
      throws BusinessException;
}
