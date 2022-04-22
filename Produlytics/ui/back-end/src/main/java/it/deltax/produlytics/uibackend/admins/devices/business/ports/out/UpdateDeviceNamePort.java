package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;

/**
 * La porta per l'aggiornamento del nome di una macchina
 */
public interface UpdateDeviceNamePort {
	void updateDeviceName(DetailedDevice device);
}
