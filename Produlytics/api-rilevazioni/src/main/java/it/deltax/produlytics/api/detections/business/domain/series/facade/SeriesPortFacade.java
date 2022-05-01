package it.deltax.produlytics.api.detections.business.domain.series.facade;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import java.util.List;

/**
 * Questa interfaccia raccoglie insieme l'interfaccia di alcune porte che sono usate sempre insieme.
 */
public interface SeriesPortFacade {
  /**
   * Questo metodo è equivalente all'omonimo definito in {@code InsertDetectionPort}.
   *
   * @param detection la rilevazione da persistere
   */
  void insertDetection(Detection detection);

  /**
   * Questo metodo è equivalente all'omonimo definito in {@code FindLastDetectionsPort}.
   *
   * @param characteristicId l'identificativo globale della caratteristica di cui cercare le
   *     rilevazioni, la quale deve esistere
   * @param count il numero massimo di rilevazioni da ottenere
   * @return una lista delle ultime {@code count} rilevazioni della caratteristica con identificativo
   *     `characteristicId, * o meno se non ce ne sono abbastanza
   */
  List<Detection> findLastDetections(CharacteristicId characteristicId, int count);

  /**
   * Questo metodo è equivalente all'omonimo definito in {@code MarkOutlierPort}.
   *
   * @param detection la rilevazione da marcare come anomala
   */
  void markOutlier(Detection detection);
}
