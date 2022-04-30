package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;
import lombok.AllArgsConstructor;

/** Questa classe implementa un insieme di carte di controllo. */
@AllArgsConstructor
public class ControlChartsGroupImpl implements ControlChartsGroup {
  /** Una lista di carte di controllo. */
  private final List<? extends ControlChart> controlCharts;

  /**
   * Implementazione dell'omonimo metodo definito in {@code ControlChartsGroup}.
   *
   * @return il numero di rilevazioni richiesto dalle carte di controllo contenute in questa classe
   */
  @Override
  public int requiredDetectionCount() {
    return this.controlCharts.stream()
        .mapToInt(ControlChart::requiredDetectionCount)
        .max()
        .orElse(0);
  }

  /**
   * Implementazione dell'omonimo metodo definito in {@code ControlChartsGroup}.
   *
   * @param detections le rilevazioni da analizzare
   * @param detections le rilevazioni da analizzare
   */
  @Override
  public void analyzeDetections(
      List<? extends MarkableDetection> detections, ControlLimits limits) {
    for (ControlChart controlChart : this.controlCharts) {
      // Evita d'interrogare la carta di controllo se non ci sono abbastanza rilevazioni.
      int count = controlChart.requiredDetectionCount();
      if (detections.size() < count) {
        continue;
      }
      // Passa solo le ultime rilevazioni richieste dalla carta di controllo.
      controlChart.analyzeDetections(this.cutLastDetections(detections, count), limits);
    }
  }

  /**
   * Ritorna una lista contenente solo le ultime {@code count} rilevazioni di {@code lastDetections}.
   *
   * @param lastDetections una lista di rilevazioni marcabili come anomale
   * @param count il numero delle ultime rilevazioni da ritornare. Deve essere maggiore o uguale
   *     alla lunghezza di {@code lastDetections}
   * @return le ultime {@code count} rilevazioni
   */
  private List<? extends MarkableDetection> cutLastDetections(
      List<? extends MarkableDetection> lastDetections, int count) {
    return lastDetections.subList(lastDetections.size() - count, lastDetections.size());
  }
}
