package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

public interface UpdateDeviceDeactivateStatusUseCase {
	void updateDeviceDeactivateStatus(DeviceDeactivateStatus device) throws BusinessException;
}
