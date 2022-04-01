package it.deltax.produlytics.uibackend.admins.business.ports.out;

public interface UpdateDeviceDeactivateStatusPort {
	void updateDeviceDeactivateStatus(int deviceId, boolean deactivated);
}
