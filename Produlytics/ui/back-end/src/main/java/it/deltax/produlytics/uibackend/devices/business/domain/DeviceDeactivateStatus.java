package it.deltax.produlytics.uibackend.devices.business.domain;

public record DeviceDeactivateStatus(
	int deviceId,
	boolean deactivated
) {
}
