package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import it.deltax.produlytics.uibackend.devices.business.domain.DetailedCharacteristic;

import java.util.List;

public record DeviceToInsert(
	String name,
	List<DetailedCharacteristic> characteristics
) {}
