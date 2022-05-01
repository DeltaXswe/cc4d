package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import lombok.Builder;

/**
 * Record che rappresenta una caratteristica con tutti i suoi dati.
 * Mette a disposizione un builder con valori di default.
 */
public record DetailedCharacteristic(
    int id,
    int deviceId,
    String name,
    OptionalDouble lowerLimit,
    OptionalDouble upperLimit,
    boolean autoAdjust,
    OptionalInt sampleSize,
    boolean archived
) {
  @Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
  public DetailedCharacteristic {}

  /**
    * Fornisce il builder del record.

    * @return un nuovo builder con i seguenti valori di default:
    *     upperLimit: empty
    *     lowerLimit: empty
    *     sampleSize: empty
    *     archived: false
  */
  public static DetailedCharacteristic.DetailedCharacteristicBuilder builder() {
    return new DetailedCharacteristicBuilder()
      .withUpperLimit(OptionalDouble.empty())
      .withLowerLimit(OptionalDouble.empty())
      .withSampleSize(OptionalInt.empty())
      .withArchived(false);
  }
}
