package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface InsertDeviceUseCase {
	int insertDevice(DeviceToInsert device) throws BusinessException;
}
