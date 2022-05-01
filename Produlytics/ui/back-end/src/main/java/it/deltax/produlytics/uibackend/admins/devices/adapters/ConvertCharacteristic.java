package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * Interfaccia che fornisce i metodi per convertire oggetti delle caratteristiche utilizzati nella
 * business logic in oggetti utilizzati nel repository e l'inverso.
 */
public interface ConvertCharacteristic {
  /**
   * Converte un oggetto CharacteristicEntity in uno DetailedCharacteristic.
   *
   * @param characteristic la CharacteristicEntity da convertire
   * @return la DetailedCharacteristic equivalente
   */
  static DetailedCharacteristic toDetailed(CharacteristicEntity characteristic) {
    return DetailedCharacteristic.builder()
        .withId(characteristic.getId())
        .withDeviceId(characteristic.getDeviceId())
        .withName(characteristic.getName())
        .withLowerLimit(
            characteristic.getLowerLimit() != null
                ? OptionalDouble.of(characteristic.getLowerLimit())
                : OptionalDouble.empty())
        .withUpperLimit(
            characteristic.getUpperLimit() != null
                ? OptionalDouble.of(characteristic.getSampleSize())
                : OptionalDouble.empty())
        .withAutoAdjust(characteristic.getAutoAdjust())
        .withSampleSize(
            characteristic.getSampleSize() != null
                ? OptionalInt.of(characteristic.getSampleSize())
                : OptionalInt.empty())
        .withArchived(characteristic.getArchived())
        .build();
  }

  /**
   * Converte un oggetto DetailedCharacteristic in uno CharacteristicEntity.
   *
   * @param characteristic la DetailedCharacteristic da convertire
   * @return la CharacteristicEntity equivalente
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
        characteristic.archived());
  }
}
