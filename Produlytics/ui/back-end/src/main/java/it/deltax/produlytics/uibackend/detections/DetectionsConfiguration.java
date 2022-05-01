package it.deltax.produlytics.uibackend.detections;

import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.detections.business.services.GetDetectionsService;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Classe per la configurazione di Spring che descrive come creare le classi di business. */
@Configuration
public class DetectionsConfiguration {
  /**
   * Crea un'istanza di GetDetectionsUseCase.
   *
   * @param findDetectionsPort la porta per trovare le rilevazioni, da passare al costruttore di
   *     GetDetectionsService
   * @param findCharacteristicPort la porta per trovare una caratteristica, da passare al
   *     costruttore di GetDetectionsService
   * @return la nuova istanza di GetDetectionsService
   */
  @Bean
  GetDetectionsUseCase getDetectionsUseCase(
      FindAllDetectionsPort findDetectionsPort,
      FindCharacteristicLimitsPort findCharacteristicPort) {
    return new GetDetectionsService(findDetectionsPort, findCharacteristicPort);
  }
}
