package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * L'interfaccia che rappresenta il caso d'uso di aggiornamento dello stato di archiviazione di una macchina
 */
public interface UpdateDeviceArchiveStatusUseCase {
	void updateDeviceArchiveStatus(DeviceArchiveStatus device) throws BusinessException;
}
