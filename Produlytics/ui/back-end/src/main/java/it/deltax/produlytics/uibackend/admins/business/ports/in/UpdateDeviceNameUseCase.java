package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateDeviceNameUseCase {
	void updateDeviceName(TinyDevice device) throws BusinessException;
}
