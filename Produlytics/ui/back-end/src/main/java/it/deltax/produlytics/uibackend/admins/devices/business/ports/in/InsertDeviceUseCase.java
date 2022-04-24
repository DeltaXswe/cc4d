package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Interfaccia che rappresenta il caso d'uso di inserimento di una macchina
 */
public interface InsertDeviceUseCase {
	int insertDevice(DeviceToInsert device) throws BusinessException;
}
