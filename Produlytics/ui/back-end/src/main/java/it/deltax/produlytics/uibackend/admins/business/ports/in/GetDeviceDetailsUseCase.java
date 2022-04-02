package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.DeviceDetails;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

import java.util.List;
import java.util.Optional;

public interface GetDeviceDetailsUseCase {
	Optional<DeviceDetails> getDeviceDetails(int id) throws BusinessException;
}
