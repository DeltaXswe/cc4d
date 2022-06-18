package it.deltax.produlytics.uibackend.repositories;

/**
 * Questa interfaccia descrive il risultato delle query definita nei metodi {@code
 * meanStddevWithSampleSize} e {@code meanStddevWithoutSampleSize} di {@code
 * CharacteristicRepository}, lasciando a Spring il compito d'implementarla.
 */
public interface MeanStddevEntity {
  /**
   * Ritorna il valore della media calcolata con l'auto-adjust.
   *
   * @return il valore della media
   */
  double getMean();

  /**
   * Ritorna il valore della deviazione standard calcolata con l'auto-adjust.
   *
   * @return il valore della deviazione standard
   */
  double getStddev();
}
