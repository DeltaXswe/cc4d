package it.deltax.produlytics.api.detections.business.domain.series;

import it.deltax.produlytics.api.detections.business.domain.Detection;

/**
 * Questa interfaccia descrive l'abilità di processare una serie di rilevazioni, tutte relative a
 * una singola caratteristica.
 */
public interface DetectionSeries {
  /**
   * Questo metodo inserisce una nuova rilevazione nella serie, processandola.
   *
   * @param rawDetection la rilevazione da processare. Deve appartenere alla caratteristica
   *     associata a questa serie
   */
  void insertDetection(Detection rawDetection);
}
