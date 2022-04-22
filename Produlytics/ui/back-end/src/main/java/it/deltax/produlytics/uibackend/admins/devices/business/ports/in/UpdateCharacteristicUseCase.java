package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicToUpdate;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * L'interfaccia che rappresenta il caso d'uso di aggiornamento di una caratteristica
 */
public interface UpdateCharacteristicUseCase {
	void updateCharacteristic(CharacteristicToUpdate toUpdate) throws BusinessException;
}
