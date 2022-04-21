package it.deltax.produlytics.api.detections.web;

import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Questa classe è un controller di Spring Boot. Il suo ruolo è essere il punto d'entrata delle
 * richieste HTTP all'endpoint /detections e inoltrarle al service della parte di business.
 */
@RestController
@RequestMapping("/detections")
@SuppressWarnings("unused")
public class DetectionsController {
  /**
   * L'istanza di un servizio a cui inoltrare le nuove rilevazioni in entrata e che si occuperà di
   * processarle.
   */
  private final ProcessIncomingDetectionUseCase processIncomingDetectionUseCase;

  /**
   * Costruisce una nuova istanza di `DetectionsController`.
   *
   * @param processIncomingDetectionUseCase Il valore per il campo
   *     `processIncomingDetectionUseCase`.
   */
  public DetectionsController(ProcessIncomingDetectionUseCase processIncomingDetectionUseCase) {
    this.processIncomingDetectionUseCase = processIncomingDetectionUseCase;
  }

  /**
   * Questo metodo riceve le richieste HTTP fatte all'API e si occupa d'inoltrarle al relativo
   * servizio di business.
   *
   * @param incomingDetection Il body della richiesta HTTP.
   */
  @PostMapping("")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void addDetection(@RequestBody IncomingDetection incomingDetection)
      throws BusinessException {
    processIncomingDetectionUseCase.processIncomingDetection(incomingDetection);
  }
}
