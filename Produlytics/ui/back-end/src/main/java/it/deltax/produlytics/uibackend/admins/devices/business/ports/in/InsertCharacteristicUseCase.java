package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * L'interfaccia che rappresenta il caso d'uso di inserimento di una caratteristica
 */
public interface InsertCharacteristicUseCase {
	int insertByDevice(int deviceId, NewCharacteristic characteristic) throws BusinessException;
}
