package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

import java.util.List;

public interface GetCharacteristicsUseCase {
	public List<Characteristic> getByDevice(int deviceId) throws BusinessException;
}
