package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.validate.DeviceInfo;

import java.util.Optional;

/**
 * Questa interfaccia descrive l'abilità di poter cercare una macchina tra quelle memorizzate
 * tramite la sua chiave API e permette di separare la sua implementazione dalla logica di business.
 */
public interface FindDeviceByApiKeyPort {
  /**
   * Questo metodo cerca i dettagli di una macchina e se esiste li ritorna, altrimenti ritorna
   * `Optional.empty()`.
   *
   * @param apiKey La chiave API della macchina da cercare. Potrebbe non esistere.
   * @return Le informazioni della macchina se esiste, altrimenti `Optional.empty()`.
   */
  Optional<DeviceInfo> findDeviceByApiKey(String apiKey);
}
