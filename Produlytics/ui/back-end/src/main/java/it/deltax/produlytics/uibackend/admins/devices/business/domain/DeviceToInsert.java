package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import java.util.List;

public record DeviceToInsert(
	String name,
	List<NewCharacteristic> characteristics
) {}
