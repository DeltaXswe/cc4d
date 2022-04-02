package it.deltax.produlytics.uibackend.devices.business.ports.out;

public interface UpdateDeviceArchiveStatusPort {
	void updateDeviceArchiveStatus(int deviceId, boolean archived);
}
