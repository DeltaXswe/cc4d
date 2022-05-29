package it.deltax.produlytics.api.repositories;

import java.util.Optional;

/**
 * Questa interfaccia descrive il risultato della query definita nel metodo {@code findLimits} di
 * {@code CharacteristicRepository}, lasciando a Spring il compito d'implementarla.
 */
public interface LimitsEntity {
  /**
   * Ritorna se l'auto-adjust è abilitato o meno.
   *
   * @return {@code true} se l'auto-adjust è abilitato, {@code false} altrimenti
   */
  boolean getAutoAdjust();

  /**
   * Ritorna il valore del limite tecnico inferiore, se presente.
   *
   * @return il valore del limite tecnico inferiore, se presente, altrimenti {@code Optional.empty()}
   */
  Optional<Double> getTechnicalLowerLimit();

  /**
   * Ritorna il valore del limite tecnico superiore, se presente.
   *
   * @return il valore del limite tecnico superiore, se presente, altrimenti {@code Optional.empty()}
   */
  Optional<Double> getTechnicalUpperLimit();

  /**
   * Ritorna il valore della media calcolata con l'auto-adjust.
   *
   * @return il valore della media, se è attivato l'auto-adjust, altrimenti un valore arbitrario
   */
  double getComputedMean();

  /**
   * Ritorna il valore della deviazione standard calcolata con l'auto-adjust.
   *
   * @return il valore della deviazione standard, se è attivato l'auto-adjust, altrimenti un valore
   *     arbitrario
   */
  double getComputedStddev();
}
