package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

public interface UpdateDeviceArchiveStatusUseCase {
	void updateDeviceArchiveStatus(DeviceArchiveStatus device) throws BusinessException;
}
