package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;

/**
 * Questa classe implementa la carta di controllo che identifica
 * se 2 punti su 3 consecutivi sono all'interno di una delle due zone A o oltre.
 */
public class ControlChartZoneA implements ControlChart {
  /**
   * Implementazione dell'omonimo metodo definito in {@code ControlChart}.
   *
   * @return il numero di rilevazioni richieste per l'analisi
   */
  @Override
  public int requiredDetectionCount() {
    return 3;
  }

  /**
   * Implementazione dell'omonimo metodo definito in {@code ControlChart}.
   *
   * @param detections le rilevazioni da analizzare. Deve avere la lunghezza specificata da
   *     {@code requiredDetectionCount}
   * @param limits i limiti di controllo della caratteristica a cui appartengono le rilevazioni
   */
  @Override
  public void analyzeDetections(
      List<? extends MarkableDetection> detections, ControlLimits limits) {
    double lowerZone = limits.lowerABLimit();
    long inLowerZone =
        detections.stream().filter(detection -> detection.value() < lowerZone).count();

    double upperZone = limits.upperABLimit();
    long inUpperZone =
        detections.stream().filter(detection -> detection.value() > upperZone).count();

    if (inLowerZone >= 2 || inUpperZone >= 2) {
      ControlChartUtils.markAll(detections);
    }
  }
}
