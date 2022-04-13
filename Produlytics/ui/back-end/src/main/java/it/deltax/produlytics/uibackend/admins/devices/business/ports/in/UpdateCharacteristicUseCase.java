package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicToUpdate;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateCharacteristicUseCase {
	void updateCharacteristic(CharacteristicToUpdate toUpdate) throws BusinessException;
}
