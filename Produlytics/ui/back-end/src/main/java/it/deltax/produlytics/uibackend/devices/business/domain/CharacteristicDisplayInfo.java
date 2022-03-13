package it.deltax.produlytics.uibackend.devices.business.domain;

public record CharacteristicDisplayInfo(
	UnarchivedDeviceInfo machine,
	Characteristic characteristic
) {
}
