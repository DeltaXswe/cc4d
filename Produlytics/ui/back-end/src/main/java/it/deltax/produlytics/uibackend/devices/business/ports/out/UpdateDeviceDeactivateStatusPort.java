package it.deltax.produlytics.uibackend.devices.business.ports.out;

public interface UpdateDeviceDeactivateStatusPort {
	void updateDeviceDeactivateStatus(int deviceId, boolean deactivated);
}
