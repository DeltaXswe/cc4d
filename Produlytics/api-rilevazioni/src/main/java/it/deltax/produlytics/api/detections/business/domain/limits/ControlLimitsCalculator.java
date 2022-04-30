package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;

/** Questa interfaccia si occupa di calcolare i limiti di controllo di una caratteristica. */
public interface ControlLimitsCalculator {
  /**
   * Questo metodo si occupa di calcolare i limiti di controllo di una caratteristica, ritornandoli.
   *
   * @param characteristicId l'identificativo globale della caratteristica di cui calcolare i limiti
   *     di controllo
   * @return i limiti di controllo della caratteristica identificata da {@code characteristicId}
   */
  ControlLimits calculateControlLimits(CharacteristicId characteristicId);
}
