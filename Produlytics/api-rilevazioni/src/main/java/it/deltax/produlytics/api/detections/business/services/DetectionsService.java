package it.deltax.produlytics.api.detections.business.services;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.exceptions.BusinessException;

import java.time.Instant;

/** Questa classe si occupa di gestire le rilevazioni in arrivo da una macchina. */
public class DetectionsService implements ProcessIncomingDetectionUseCase {
  /**
   * Un'istanza di `DetectionValidator` a cui delegare il compito di validare una rilevazione in
   * arrivo.
   */
  private final DetectionValidator detectionValidator;
  /**
   * Un'istanza di `DetectionQueue` a cui delegare il compito di processare le rilevazioni in
   * background.
   */
  private final DetectionQueue detectionQueue;

  /**
   * Crea una nuova istanza di `DetectionsService`.
   *
   * @param detectionValidator Il valore per il campo `detectionValidator`.
   * @param detectionQueue Il valore per il campo `detectionQueue`.
   */
  public DetectionsService(DetectionValidator detectionValidator, DetectionQueue detectionQueue) {
    this.detectionValidator = detectionValidator;
    this.detectionQueue = detectionQueue;
  }

  /**
   * Questo metodo implementa l'omonimo metodo definito in `ProcessIncomingDetectionUseCase`.
   *
   * @param incomingDetection la rilevazione in arrivo da processare;
   * @throws BusinessException nel caso in cui la chiave API non sia corretta, se la macchina o
   *     caratteristica non esistano, siano archiviate o siano disattivate.
   */
  @Override
  public void processIncomingDetection(IncomingDetection incomingDetection)
      throws BusinessException {
    Instant now = Instant.now();
    CharacteristicId characteristicId =
        detectionValidator.validateAndFindId(
            incomingDetection.apiKey(), incomingDetection.characteristic());
    Detection detection = new Detection(characteristicId, now, incomingDetection.value());
    detectionQueue.enqueueDetection(detection);
  }
}
