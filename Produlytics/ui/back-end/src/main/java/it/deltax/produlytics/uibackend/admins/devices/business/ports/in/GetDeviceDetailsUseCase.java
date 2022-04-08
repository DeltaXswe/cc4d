package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

import java.util.Optional;

public interface GetDeviceDetailsUseCase {
	DetailedDevice getDeviceDetails(int id) throws BusinessException;
}
