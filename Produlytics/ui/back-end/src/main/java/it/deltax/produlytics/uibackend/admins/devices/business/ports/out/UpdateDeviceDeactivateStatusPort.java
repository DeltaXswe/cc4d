package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.DetailedDevice;

public interface UpdateDeviceDeactivateStatusPort {
	void updateDeviceDeactivateStatus(DetailedDevice device);
}
