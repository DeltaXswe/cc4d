package it.deltax.produlytics.uibackend.admins.business.domain;

public record DeviceDeactivateStatus(
	int deviceId,
	boolean deactivated
) {
}
