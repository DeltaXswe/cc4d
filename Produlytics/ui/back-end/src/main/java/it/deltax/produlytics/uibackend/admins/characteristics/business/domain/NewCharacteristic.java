package it.deltax.produlytics.uibackend.admins.characteristics.business.domain;

import lombok.Builder;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public record NewCharacteristic(
	String name,
	OptionalDouble upperLimit,
	OptionalDouble lowerLimit,
	boolean autoAdjust,
	OptionalInt sampleSize,
	boolean archived
) {
	@Builder(builderMethodName = "", setterPrefix = "with")
	public NewCharacteristic {}

	public static NewCharacteristicBuilder toBuilder() {
		return new NewCharacteristicBuilder()
			.withArchived(false)
			.withUpperLimit(OptionalDouble.empty())
			.withLowerLimit(OptionalDouble.empty())
			.withSampleSize(OptionalInt.empty());
	}
}
