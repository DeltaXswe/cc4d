package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateDeviceArchiveStatusUseCase {
	void updateDeviceArchiveStatus(DeviceArchiveStatus device) throws BusinessException;
}
