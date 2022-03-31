package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.admins.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateDeviceArchiveStatusUseCase {
	void modDevArchStatus(DeviceArchiveStatus device) throws BusinessException;
}
