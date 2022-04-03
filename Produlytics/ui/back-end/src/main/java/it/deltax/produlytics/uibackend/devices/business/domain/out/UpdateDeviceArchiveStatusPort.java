package it.deltax.produlytics.uibackend.devices.business.domain.out;

import it.deltax.produlytics.uibackend.devices.business.domain.DetailedDevice;

public interface UpdateDeviceArchiveStatusPort {
	void updateDeviceArchiveStatus(DetailedDevice device);
}
