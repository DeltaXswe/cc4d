package it.deltax.produlytics.uibackend.admins.business.ports.out;

public interface UpdateDeviceArchiveStatusPort {
	void updateDeviceArchiveStatus(int deviceId, boolean archived);
}
