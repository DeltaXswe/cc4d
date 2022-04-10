package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import java.util.List;

/**
 * Il record rappresenta una macchina da inserire, con le suo caratteristiche
 * @author Leila Dardouri
 */
public record DeviceToInsert(
	String name,
	List<NewCharacteristic> characteristics
) {}