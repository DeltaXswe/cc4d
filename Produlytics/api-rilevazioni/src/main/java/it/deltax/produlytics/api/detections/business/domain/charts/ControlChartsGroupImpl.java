package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import lombok.AllArgsConstructor;

import java.util.List;

/** Questa classe implementa un insieme di carte di controllo. */
@AllArgsConstructor
public class ControlChartsGroupImpl implements ControlChartsGroup {
  /** Una lista di carte di controllo. */
  private final List<? extends ControlChart> controlCharts;

  /**
   * Implementazione dell'omonimo metodo definito in `ControlChartsGroup`.
   *
   * @return Il numero di rilevazioni richiesto dalle carte di controllo contenute in questa classe.
   */
  @Override
  public int requiredDetectionCount() {
    return this.controlCharts.stream()
        .mapToInt(ControlChart::requiredDetectionCount)
        .max()
        .orElse(0);
  }

  /**
   * Implementazione dell'omonimo metodo definito in `ControlChartsGroup`.
   *
   * @param detections Le rilevazioni da analizzare.
   * @param limits I limiti di controllo della caratteristica a cui appartengono le rilevazioni.
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
   * Ritorna una lista contenente solo le ultime `count` rilevazioni di `lastDetections`.
   *
   * @param lastDetections Una lista di rilevazioni marcabili come anomale.
   * @param count Il numero delle ultime rilevazioni da ritornare. Deve essere maggiore o uguale
   *     alla lunghezza di `lastDetections`.
   * @return Le ultime `count` rilevazioni.
   */
  private List<? extends MarkableDetection> cutLastDetections(
      List<? extends MarkableDetection> lastDetections, int count) {
    return lastDetections.subList(lastDetections.size() - count, lastDetections.size());
  }
}
