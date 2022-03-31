package it.deltax.produlytics.uibackend.admins.business.ports.out;

public interface UpdateDeviceArchiveStatus {
	void updateDeviceArchiveStatus(int deviceId, boolean archived);
}
