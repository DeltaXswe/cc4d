package it.deltax.produlytics.api.detections.business.domain.series.facade;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;
import java.util.List;
import lombok.AllArgsConstructor;

/** Questa classe implementa l'interfaccia di alcune porte che sono usate sempre insieme. */
@AllArgsConstructor
public class SeriesPortFacadeImpl implements SeriesPortFacade {
  /** La porta a cui viene inoltrato {@code insertDetection}. */
  private final InsertDetectionPort insertDetectionPort;
  /** La porta a cui viene inoltrato {@code findLastDetections}. */
  private final FindLastDetectionsPort findLastDetectionsPort;
  /** La porta a cui viene inoltrato {@code markOutlier}. */
  private final MarkOutlierPort markOutlierPort;

  /**
   * Implementazione dell'omonimo metodo definito in {@code SeriesPortFacade}.
   *
   * @param detection la rilevazione da persistere
   */
  @Override
  public void insertDetection(Detection detection) {
    this.insertDetectionPort.insertDetection(detection);
  }

  /**
   * Implementazione dell'omonimo metodo definito in {@code SeriesPortFacade}.
   *
   * @param characteristicId l'identificativo globale della caratteristica di cui cercare le
   *     rilevazioni, la quale deve esistere
   * @param count il numero massimo di rilevazioni da ottenere
   * @return una lista delle ultime {@code count} rilevazioni della caratteristica con identificativo
   *     `characteristicId, * o meno se non ce ne sono abbastanza
   */
  @Override
  public List<Detection> findLastDetections(CharacteristicId characteristicId, int count) {
    return this.findLastDetectionsPort.findLastDetections(characteristicId, count);
  }

  /**
   * Implementazione dell'omonimo metodo definito in {@code SeriesPortFacade}.
   *
   * @param detection la rilevazione da marcare come anomala
   */
  @Override
  public void markOutlier(Detection detection) {
    this.markOutlierPort.markOutlier(detection);
  }
}
