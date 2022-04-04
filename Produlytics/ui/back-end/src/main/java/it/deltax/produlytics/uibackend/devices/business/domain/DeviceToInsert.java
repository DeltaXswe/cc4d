package it.deltax.produlytics.uibackend.devices.business.domain;

import java.util.List;

public record DeviceToInsert(
	String name,
	List<DetailedCharacteristic> characteristics
) {}
