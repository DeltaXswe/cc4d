package it.deltax.produlytics.api.detections.business.domain.queue;

import it.deltax.produlytics.api.detections.business.domain.Detection;

/**
 * Questa interfaccia descrive l'abilità di accodare una rilevazione per essere processata
 * successivamente in background, senza quindi bloccare l'utilizzatore.
 */
public interface DetectionQueue {
  /**
   * Questo metodo accoda una rilevazione per essere processata successivamente in background.
   *
   * @param detection la rilevazione da accodare
   */
  void enqueueDetection(Detection detection);

  /**
   * Questo metodo permette di chiudere la coda, bloccando il chiamante finchè tutte le rilevazioni
   * non siano processate.
   */
  void close();
}
