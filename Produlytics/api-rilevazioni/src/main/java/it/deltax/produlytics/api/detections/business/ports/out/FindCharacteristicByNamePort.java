package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicInfo;

import java.util.Optional;

/**
 * Questa interfaccia descrive l'abilità di poter cercare una caratteristica tra quelle memorizzate
 * tramite il suo identificativo e permette di separare la sua implementazione dalla logica di
 * business.
 */
public interface FindCharacteristicByNamePort {

  /**
   * Questo metodo cerca i dettagli di una caratteristica dato l'identificativo della macchina a cui
   * appartiene e il suo nome e se esiste li ritorna, altrimenti ritorna `Optional.empty()`.
   *
   * @param deviceId L'identificativo della macchina, che deve esistere, a cui appartiene la
   *     caratteristica da cercare.
   * @param name Il nome della caratteristica da cercare.
   * @return Le informazioni della caratteristica se esiste, altrimenti `Optional.empty()`.
   */
  Optional<CharacteristicInfo> findCharacteristicByName(int deviceId, String name);
}
