package it.deltax.produlytics.uibackend.admins.business.domain;

public record DeviceArchiveStatus(
	int deviceId,
	boolean archived
) {
}
