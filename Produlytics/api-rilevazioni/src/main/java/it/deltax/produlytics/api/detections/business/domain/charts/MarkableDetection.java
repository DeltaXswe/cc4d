package it.deltax.produlytics.api.detections.business.domain.charts;

/** Questa interfaccia rappresenta una rilevazione che può essere marcata come anomala. */
public interface MarkableDetection {
  /**
   * Ritorna il valore della rilevazione.
   *
   * @return il valore della rilevazione
   */
  double value();

  /** Marca la rilevazione come anomala. */
  void markOutlier();
}
