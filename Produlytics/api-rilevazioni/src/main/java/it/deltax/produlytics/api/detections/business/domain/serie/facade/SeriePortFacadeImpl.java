package it.deltax.produlytics.api.detections.business.domain.serie.facade;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;
import lombok.AllArgsConstructor;

import java.util.List;

/** Questa classe implementa l'interfaccia di alcune porte che sono usate sempre insieme. */
@AllArgsConstructor
public class SeriePortFacadeImpl implements SeriePortFacade {
  /** La porta a cui viene inoltrato `insertDetection`. */
  private final InsertDetectionPort insertDetectionPort;
  /** La porta a cui viene inoltrato `findLastDetections`. */
  private final FindLastDetectionsPort findLastDetectionsPort;
  /** La porta a cui viene inoltrato `markOutlier`. */
  private final MarkOutlierPort markOutlierPort;

  /**
   * Implementazione dell'omonimo metodo definito in `SeriePortFacade`.
   *
   * @param detection La rilevazione da persistere.
   */
  @Override
  public void insertDetection(Detection detection) {
    this.insertDetectionPort.insertDetection(detection);
  }

  /**
   * Implementazione dell'omonimo metodo definito in `SeriePortFacade`.
   *
   * @param characteristicId L'identificativo globale della caratteristica di cui cercare le
   *     rilevazioni, la quale deve esistere.
   * @param count Il numero massimo di rilevazioni da ottenere.
   * @return Una lista delle ultime `count` rilevazioni della caratteristica con identificativo
   *     `characteristicId, * o meno se non ce ne sono abbastanza.
   */
  @Override
  public List<Detection> findLastDetections(CharacteristicId characteristicId, int count) {
    return this.findLastDetectionsPort.findLastDetections(characteristicId, count);
  }

  /**
   * Implementazione dell'omonimo metodo definito in `SeriePortFacade`.
   *
   * @param detection La rilevazione da marcare come anomala.
   */
  @Override
  public void markOutlier(Detection detection) {
    this.markOutlierPort.markOutlier(detection);
  }
}
