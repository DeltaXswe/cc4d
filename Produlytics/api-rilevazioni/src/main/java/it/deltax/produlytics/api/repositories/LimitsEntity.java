package it.deltax.produlytics.api.repositories;

import java.util.Optional;

/**
 * Questa interfaccia descrive il risultato della query definita nel metodo `findLimits` di
 * `CharacteristicRepository`, lasciando a Spring il compito d'implementarla.
 */
public interface LimitsEntity {
  /**
   * Ritorna se l'auto-adjust è abilitato o meno.
   *
   * @return `true` se l'auto-adjust è abilitato, `false` altrimenti.
   */
  boolean getAutoAdjust();

  /**
   * Ritorna il valore del limite tecnico inferiore, se presente;
   *
   * @return Il valore del limite tecnico inferiore, se presente, altrimenti `Optional.empty()`.
   */
  Optional<Double> getTechnicalLowerLimit();

  /**
   * Ritorna il valore del limite tecnico superiore, se presente;
   *
   * @return Il valore del limite tecnico superiore, se presente, altrimenti `Optional.empty()`.
   */
  Optional<Double> getTechnicalUpperLimit();

  /**
   * Ritorna il valore della media calcolata con l'auto-adjust;
   *
   * @return Il valore della media, se è attivato l'auto-adjust, altrimenti un valore arbitrario.
   */
  double getComputedMean();

  /**
   * Ritorna il valore della deviazione standard calcolata con l'auto-adjust;
   *
   * @return Il valore della deviazione standard, se è attivato l'auto-adjust, altrimenti un valore
   *     arbitrario.
   */
  double getComputedStddev();
}
