package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.Detection;

/**
 * Questa interfaccia descrive l'abilità di poter marcare una rilevazione come anomala e permette di
 * separare la sua implementazione dalla logica di business.
 */
public interface MarkOutlierPort {
  /**
   * Questo metodo si occupa di marcare una rilevazione come anomala.
   *
   * @param detection La rilevazione da marcare come anomala.
   */
  void markOutlier(Detection detection);
}
