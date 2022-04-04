package it.deltax.produlytics.uibackend.devices.business.domain;

public record DetailedCharacteristic(
	int id,
	double lowerLimit,
	double upperLimit,
	boolean autoAdjust,
	int sampleSize
) {}
