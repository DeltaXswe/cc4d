package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartsGroup;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

import java.util.List;

/**
 * Questa classe si occupa di processare una serie di rilevazioni relative a una stessa
 * caratteristica.
 */
public class DetectionSerieImpl implements DetectionSerie {
  /** Le porte utilizzate da questa classe. */
  private final SeriePortFacade ports;
  /** Un calcolatore di limiti di controllo. */
  private final ControlLimitsCalculator controlLimitsCalculator;
  /** Le carte di controllo da applicare. */
  private final ControlChartsGroup controlChartsGroup;
  /** L'identificativo globale della caratteristica associata a questa serie. */
  private final CharacteristicId characteristicId;

  /**
   * Crea una nuova istanza di `DetectionSerieImpl`.
   *
   * @param ports Il valore per il campo `ports`.
   * @param controlLimitsCalculator Il valore per il campo `controlLimitsCalculator`.
   * @param controlChartsGroup Il valore per il campo `controlChartsGroup`.
   * @param characteristicId Il valore per il campo `characteristicId`.
   */
  DetectionSerieImpl(
      SeriePortFacade ports,
      ControlLimitsCalculator controlLimitsCalculator,
      ControlChartsGroup controlChartsGroup,
      CharacteristicId characteristicId) {
    this.ports = ports;
    this.controlLimitsCalculator = controlLimitsCalculator;
    this.controlChartsGroup = controlChartsGroup;
    this.characteristicId = characteristicId;
  }

  /**
   * Questo metodo implementa l'omonimo metodo definito in `DetectionSerie`.
   *
   * @param detection La rilevazione da processare. Deve appartenere alla caratteristica associata a
   *     questa serie.
   */
  @Override
  public void insertDetection(Detection detection) {
    this.ports.insertDetection(detection);

    ControlLimits controlLimits =
        this.controlLimitsCalculator.calculateControlLimits(this.characteristicId);
    List<? extends MarkableDetection> lastDetections = this.detectionsForControlCharts();
    this.controlChartsGroup.analyzeDetections(lastDetections, controlLimits);
  }

  /**
   * Questo metodo si occupa di preparare le ultime rilevazioni da passare alle carte di controllo.
   *
   * @return Una lista di `MarkableDetection` per le carte di controllo di `controlChartsGroup`.
   */
  private List<? extends MarkableDetection> detectionsForControlCharts() {
    int count = this.controlChartsGroup.requiredDetectionCount();
    return this.ports.findLastDetections(this.characteristicId, count).stream()
        .map(detection -> new MarkableDetectionAdapter(this.ports, detection))
        .toList();
  }
}
