package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.limits.LimitsInfo;

/**
 * Questa interfaccia descrive l'abilità di poter cercare i limiti tecnici e di processo di una
 * caratteristica tramite il suo identificativo e permette di separare la sua implementazione dalla
 * logica di business.
 */
public interface FindLimitsPort {
  /**
   * Questo metodo ritorna i limiti tecnici e di processo di una caratteristica.
   *
   * @param characteristicId l'identificativo globale della caratteristica di cui ottenere i limiti,
   *     la quale deve esistere
   * @return i limiti tecnici e di processo della caratteristica cercata
   */
  LimitsInfo findLimits(CharacteristicId characteristicId);
}
