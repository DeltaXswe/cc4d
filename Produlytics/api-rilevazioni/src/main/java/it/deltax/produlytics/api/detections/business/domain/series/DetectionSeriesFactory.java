package it.deltax.produlytics.api.detections.business.domain.series;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;

/**
 * Questa interfaccia descrive l'abilità di creare una nuova {@code DetectionSeries} per una data
 * caratteristica.
 */
public interface DetectionSeriesFactory {
  /**
   * Questo metodo crea una nuova {@code DetectionSeries} per una data caratteristica.
   *
   * @param characteristicId l'identificativo globale della caratteristica a cui sarà associata la
   *     {@code DetectionSeries}
   * @return una nuova istanza di {@code DetectionSeries}
   */
  DetectionSeries createSeries(CharacteristicId characteristicId);
}
