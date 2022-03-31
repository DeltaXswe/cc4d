package it.deltax.produlytics.uibackend.devices.business.domain;

import java.util.stream.DoubleStream;

public record CharacteristicLimits(
	double lowerLimit,
	double upperLimit,
	double mean
) {
	public static CharacteristicLimits newCharacteristicLimits(double lowerLimit, double upperLimit) {
		return new CharacteristicLimits(
			lowerLimit,
			upperLimit,
			mean(lowerLimit, upperLimit)
		);
	}

	private static double mean(double lowerLimit, double upperLimit) {
		return DoubleStream.of(lowerLimit, upperLimit)
			.average()
			.getAsDouble();
	}
}
