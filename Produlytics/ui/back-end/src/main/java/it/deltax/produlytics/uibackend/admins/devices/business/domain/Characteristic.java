package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import java.util.Optional;
import lombok.Builder;

/**
 * Record che rappresenta l'intestazione di una caratteristica, con
 * l'identificativo, il nome e il valore di archived.
 * Mette a disposizione un builder con valori di default
 */
public record Characteristic(
    int id,
    String name,
    boolean autoAdjust,
    Optional<Double> lowerLimit,
    Optional<Double> upperLimit,
    Optional<Integer> sampleSize,
    boolean archived
) {
  @Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
    public Characteristic {}

  /**
    * Fornisce il builder del record.
    *
    * @return un nuovo builder con archived inizializzato a false di default
  */
  public static CharacteristicBuilder builder() {
    return new CharacteristicBuilder().withArchived(false);
  }
}
