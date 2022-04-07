package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import it.deltax.produlytics.uibackend.admins.characteristics.business.domain.NewCharacteristic;

import java.util.List;

public record DeviceToInsert(
	String name,
	List<NewCharacteristic> characteristics
) {}
