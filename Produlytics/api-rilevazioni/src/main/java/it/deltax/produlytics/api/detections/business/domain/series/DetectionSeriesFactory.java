package it.deltax.produlytics.api.detections.business.domain.series;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;

/**
 * Questa interfaccia descrive l'abilità di creare una nuova `DetectionSeries` per una data
 * caratteristica.
 */
public interface DetectionSeriesFactory {
  /**
   * Questo metodo crea una nuova `DetectionSeries` per una data caratteristica.
   *
   * @param characteristicId L'identificativo globale della caratteristica a cui sarà associata la
   *     `DetectionSeries`.
   * @return Una nuova istanza di `DetectionSeries`.
   */
  DetectionSeries createSeries(CharacteristicId characteristicId);
}
