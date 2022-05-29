package it.deltax.produlytics.api.detections.business.ports.in;

import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.exceptions.BusinessException;

/**
 * Questa interfaccia modella il caso d'uso che si occupa di gestire le rilevazioni provenienti da
 * una macchina.
 */
public interface ProcessIncomingDetectionUseCase {

  /**
   * Questo metodo gestisce le nuove rilevazioni, in particolare le autentica, le persiste e
   * aggiorna quelle anomale secondo le carte di controllo. La persistenza e l'aggiornamento delle
   * anomalie non è bloccante: il metodo ritornerà non appena l'autenticazione è avvenuta.
   *
   * @param incomingDetection la rilevazione in arrivo da processare;
   * @throws BusinessException nel caso in cui la chiave API non sia corretta, se la macchina o
   *     caratteristica non esistano, siano archiviate o siano disattivate.
   */
  void processIncomingDetection(IncomingDetection incomingDetection) throws BusinessException;
}
