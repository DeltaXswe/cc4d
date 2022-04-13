package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import java.util.List;

/**
 * Il record rappresenta una macchina da inserire, con le sue caratteristiche
 */
public record DeviceToInsert(
	String name,
	List<NewCharacteristic> characteristics
) {}
