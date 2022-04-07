package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;

public interface UpdateDeviceArchiveStatusPort {
	void updateDeviceArchiveStatus(DetailedDevice device);
}
