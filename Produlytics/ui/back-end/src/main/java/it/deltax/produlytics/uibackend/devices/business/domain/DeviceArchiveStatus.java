package it.deltax.produlytics.uibackend.devices.business.domain;

public record DeviceArchiveStatus(
	int deviceId,
	boolean archived
) {
}
