package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

public interface UpdateCharacteristicArchiveStatusUseCase {
	void updateCharacteristicArchiveStatus(CharacteristicArchiveStatus toUpdate) throws BusinessException;
}
