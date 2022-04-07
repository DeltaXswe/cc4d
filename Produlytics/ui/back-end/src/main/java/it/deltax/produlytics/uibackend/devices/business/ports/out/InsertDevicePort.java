package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewDevice;

public interface InsertDevicePort {
	int insertDevice(NewDevice device);
}
