package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.DetailedDevice;

public interface UpdateDeviceArchiveStatusPort {
	void updateDeviceArchiveStatus(DetailedDevice device);
}
