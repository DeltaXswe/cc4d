package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

public interface InsertCharacteristicUseCase {
	public int insertByDevice(int deviceId, NewCharacteristic characteristic) throws BusinessException;
}
