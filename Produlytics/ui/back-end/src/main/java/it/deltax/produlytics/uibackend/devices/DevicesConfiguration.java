package it.deltax.produlytics.uibackend.devices;

import it.deltax.produlytics.uibackend.devices.business.ports.in.GetLimitsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.services.GetLimitsService;
import it.deltax.produlytics.uibackend.devices.business.services.GetUnarchivedCharacteristicsService;
import it.deltax.produlytics.uibackend.devices.business.services.GetUnarchivedDevicesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Classe per la configurazione di Spring che descrive come creare le classi di business. */
@Configuration
public class DevicesConfiguration {
  /**
   * Crea un'istanza di GetLimitsUseCase.
   *
   * @param findDevicesPort la porta cercare le macchine, da passare al costruttore di
   *     GetLimitsService
   * @param findCharacteristicLimitsPort la porta per cercare i limiti tecnici di una
   *     caratteristica, da passare al costruttore di GetLimitsService
   * @return la nuova istanza di GetLimitsService
   */
  @Bean
  GetLimitsUseCase getLimitsUseCase(
      GetUnarchivedDevicesPort findDevicesPort,
      FindCharacteristicLimitsPort findCharacteristicLimitsPort) {
    return new GetLimitsService(findDevicesPort, findCharacteristicLimitsPort);
  }

  /**
   * Crea un'istanza di GetUnarchivedCharacteristicsUseCase.
   *
   * @param findDevicesPort la porta per cercare le macchine, da passare al costruttore di
   *     GetUnarchivedCharacteristicsService
   * @param findCharacteristicsPort la porta per cercare le caratteristiche, da passare al
   *     costruttore di GetUnarchivedCharacteristicsService
   * @return la nuova istanza di GetUnarchivedCharacteristicsService
   */
  @Bean
  GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristicsUseCase(
      GetUnarchivedDevicesPort findDevicesPort,
      FindAllUnarchivedCharacteristicsPort findCharacteristicsPort) {
    return new GetUnarchivedCharacteristicsService(findDevicesPort, findCharacteristicsPort);
  }

  /**
   * Crea un'istanza di GetUnarchivedDevicesUseCase.
   *
   * @param getUnarchivedDevicesPort la porta per ottenere le macchine non archiviate, da passare al
   *     costruttore di GetUnarchivedDevicesService
   * @return la nuova istanza di GetUnarchivedDevicesService
   */
  @Bean
  GetUnarchivedDevicesUseCase getUnarchivedDevices(
      GetUnarchivedDevicesPort getUnarchivedDevicesPort) {
    return new GetUnarchivedDevicesService(getUnarchivedDevicesPort);
  }
}
