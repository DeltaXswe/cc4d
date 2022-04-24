package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;

/**
 * Questa classe implementa la carta di controllo che identifica
 * se 4 punti su 5 consecutivi sono all'interno di una delle due zone B o oltre.
 */
public class ControlChartZoneB implements ControlChart {
  /**
   * Implementazione dell'omonimo metodo definito in `ControlChart`.
   *
   * @return Il numero di rilevazioni richieste per l'analisi.
   */
  @Override
  public int requiredDetectionCount() {
    return 5;
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
    long inLowerZone =
        detections.stream().filter(detection -> detection.value() < lowerZone).count();

    double upperZone = limits.upperBCLimit();
    long inUpperZone =
        detections.stream().filter(detection -> detection.value() > upperZone).count();

    if (inLowerZone >= 4 || inUpperZone >= 4) {
      ControlChartUtils.markAll(detections);
    }
  }
}
