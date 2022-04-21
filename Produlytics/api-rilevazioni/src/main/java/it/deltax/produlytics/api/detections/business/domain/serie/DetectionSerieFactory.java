package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;

/**
 * Questa interfaccia descrive l'abilità di creare una nuova `DetectionSerie` per una data
 * caratteristica.
 */
public interface DetectionSerieFactory {
  /**
   * Questo metodo crea una nuova `DetectionSerie` per una data caratteristica.
   *
   * @param characteristicId L'identificativo globale della caratteristica a cui sarà associata la
   *     `DetectionSerie`.
   * @return Una nuova istanza di `DetectionSerie`.
   */
  DetectionSerie createSerie(CharacteristicId characteristicId);
}
