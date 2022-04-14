package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

public interface GetDeviceDetailsUseCase {
	DetailedDevice getDeviceDetails(int id) throws BusinessException;
}
