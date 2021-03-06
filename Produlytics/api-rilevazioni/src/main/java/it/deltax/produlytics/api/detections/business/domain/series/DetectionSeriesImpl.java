package it.deltax.produlytics.api.detections.business.domain.series;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartsGroup;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.series.facade.SeriesPortFacade;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * Questa classe si occupa di processare una serie di rilevazioni relative a una stessa
 * caratteristica.
 */
@AllArgsConstructor
public class DetectionSeriesImpl implements DetectionSeries {
  /** Le porte utilizzate da questa classe. */
  private final SeriesPortFacade ports;
  /** Un calcolatore di limiti di controllo. */
  private final ControlLimitsCalculator controlLimitsCalculator;
  /** Le carte di controllo da applicare. */
  private final ControlChartsGroup controlChartsGroup;
  /** L'identificativo globale della caratteristica associata a questa serie. */
  private final CharacteristicId characteristicId;

  /**
   * Questo metodo implementa l'omonimo metodo definito in {@code DetectionSeries}.
   *
   * @param detection la rilevazione da processare. Deve appartenere alla caratteristica associata a
   *     questa serie
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
   * @return una lista di {@code MarkableDetection} per le carte di controllo di {@code controlChartsGroup}
   */
  private List<? extends MarkableDetection> detectionsForControlCharts() {
    int count = this.controlChartsGroup.requiredDetectionCount();
    return this.ports.findLastDetections(this.characteristicId, count).stream()
        .map(detection -> new MarkableDetectionAdapter(this.ports, detection))
        .toList();
  }
}
