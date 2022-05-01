package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Interfaccia che rappresenta il caso d'uso di aggiornamento dello stato di archiviazione di una
 * caratteristica.
 */
public interface UpdateCharacteristicArchiveStatusUseCase {
  void updateCharacteristicArchiveStatus(CharacteristicArchiveStatus toUpdate)
      throws BusinessException;
}
