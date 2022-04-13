package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * Interfaccia che fornisce i metodi per convertire oggetti delle caratteristiche utilizzati nella business logic
 * in oggetti utilizzati nel repository e l'inverso
 */
public interface ConvertCharacteristic {
	/**
	 * Converte un oggetto <code>CharacteristicEntity</code> in uno <code>DetailedCharacteristic</code>
	 * @param characteristic la <code>CharacteristicEntity</code> da convertire
	 * @return la <code>DetailedCharacteristic</code> equivalente
	 */
	static DetailedCharacteristic toDetailed(CharacteristicEntity characteristic) {
		return DetailedCharacteristic.builder()
			.withId(characteristic.getId())
			.withDeviceId(characteristic.getDeviceId())
			.withName(characteristic.getName())
			.withLowerLimit(
				characteristic.getLowerLimit() != null
				? OptionalDouble.of(characteristic.getLowerLimit())
				: OptionalDouble.empty()
			)
			.withUpperLimit(
				characteristic.getUpperLimit() != null
				? OptionalDouble.of(characteristic.getSampleSize())
				: OptionalDouble.empty()
			)
			.withAutoAdjust(characteristic.getAutoAdjust())
			.withSampleSize(
				characteristic.getSampleSize() != null
				? OptionalInt.of(characteristic.getSampleSize())
				: OptionalInt.empty()
			)
			.withArchived(characteristic.getArchived())
			.build();
	}

	/**
	 * Converte un oggetto <code>DetailedCharacteristic</code> in uno <code>CharacteristicEntity</code>
	 * @param characteristic la <code>DetailedCharacteristic</code> da convertire
	 * @return la <code>CharacteristicEntity</code> equivalente
	 */
	static CharacteristicEntity toEntity(DetailedCharacteristic characteristic) {
		return new CharacteristicEntity(
			characteristic.id(),
			characteristic.deviceId(),
			characteristic.name(),
			characteristic.upperLimit().isPresent() ? characteristic.upperLimit().getAsDouble() : null,
			characteristic.lowerLimit().isPresent() ? characteristic.lowerLimit().getAsDouble() : null,
			characteristic.autoAdjust(),
			characteristic.sampleSize().isPresent() ? characteristic.sampleSize().getAsInt() : null,
			characteristic.archived()
		);
	}
}
