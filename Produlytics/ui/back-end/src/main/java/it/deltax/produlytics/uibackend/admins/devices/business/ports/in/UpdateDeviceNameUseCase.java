package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Interfaccia che rappresenta il caso d'uso di aggiornamento del nome di una macchina
 */
public interface UpdateDeviceNameUseCase {
	void updateDeviceName(TinyDevice device) throws BusinessException;
}
