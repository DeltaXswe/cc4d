package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;

import java.util.List;

/**
 * Questa interfaccia descrive l'abilità di poter ottenere le ultime rilevazioni di una
 * caratteristica e permette di separare la sua implementazione dalla logica di business.
 */
public interface FindLastDetectionsPort {
  /**
   * Questo metodo ritorna le ultime `count` rilevazioni della caratteristica specificata, o meno se
   * non ne esistono abbastanza.
   *
   * @param characteristicId L'identificativo globale della caratteristica a cui appartengono le
   *     rilevazioni da ottenere. Si può assumere che esista una caratteristica con tale
   *     identificativo.
   * @param count Il numero di rilevazioni da ottenere.
   * @return Una lista delle ultime `count` rilevazioni della caratteristica con identificativo
   *     `characteristicId, o meno se non ce ne sono abbastanza.
   */
  List<Detection> findLastDetections(CharacteristicId characteristicId, int count);
}
