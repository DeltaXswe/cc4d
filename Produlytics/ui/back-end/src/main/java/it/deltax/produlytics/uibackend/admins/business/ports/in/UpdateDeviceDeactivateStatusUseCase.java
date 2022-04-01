package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.admins.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateDeviceDeactivateStatusUseCase {
	void updateDeviceDeactivateStatus(DeviceDeactivateStatus device) throws BusinessException;
}
