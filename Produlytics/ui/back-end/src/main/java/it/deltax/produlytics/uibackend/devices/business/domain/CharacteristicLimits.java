package it.deltax.produlytics.uibackend.devices.business.domain;

import java.util.stream.DoubleStream;

/**
 * Record che rappresenta i limiti tecnici di una caratteristica e la loro media.
 * Mette a disposizione una static factory.
 */
public record CharacteristicLimits(
    double lowerLimit,
    double upperLimit,
    double mean
) {
  /**
    * Static factory che crea i limiti, calcolando automaticamente la media.

    * @param lowerLimit il limite tecnico inferiore
    * @param upperLimit il limite tecnico superiore

    * @return una nuova istanza del record con limiti specificati e media calcolata automaticamente.
  */
  public static CharacteristicLimits newCharacteristicLimits(double lowerLimit, double upperLimit) {
    return new CharacteristicLimits(
      lowerLimit,
      upperLimit,
      mean(lowerLimit, upperLimit)
    );
  }

  /**
    * Calcola la media dei limiti.

    * @param lowerLimit il limite tecnico inferiore
    * @param upperLimit il limite tecnico superiore
    * @return la media dei limiti specificati.
  */
  private static double mean(double lowerLimit, double upperLimit) {
    return DoubleStream.of(lowerLimit, upperLimit)
      .average()
      .getAsDouble();
  }
}
