package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;

/**
 * Questa classe implementa la carta di controllo descritta in ROF22.7, cioè identifica se 15 punti
 * consecutivi appartengono alle zone C.
 */
public class ControlChartStratification implements ControlChart {
  /**
   * Implementazione dell'omonimo metodo definito in `ControlChart`.
   *
   * @return Il numero di rilevazioni richieste per l'analisi.
   */
  @Override
  public int requiredDetectionCount() {
    return 15;
  }

  /**
   * Implementazione dell'omonimo metodo definito in `ControlChart`.
   *
   * @param detections Le rilevazioni da analizzare. Deve avere la lunghezza specificata da
   *     `requiredDetectionCount`.
   * @param limits I limiti di controllo della caratteristica a cui appartengono le rilevazioni.
   */
  @Override
  public void analyzeDetections(
      List<? extends MarkableDetection> detections, ControlLimits limits) {
    double lowerZone = limits.lowerBCLimit();
    double upperZone = limits.upperBCLimit();

    boolean allInside =
        detections.stream()
            .map(MarkableDetection::value)
            .allMatch(value -> lowerZone < value && value < upperZone);
    if (allInside) {
      ControlChartUtils.markAll(detections);
    }
  }
}
