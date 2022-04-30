package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;

/**
 * Questa classe implementa la carta di controllo che identifica
 * i punti oltre i limiti di controllo.
 */
public class ControlChartBeyondLimits implements ControlChart {
  /**
   * Implementazione dell'omonimo metodo definito in {@code ControlChart}.
   *
   * @return il numero di rilevazioni richieste per l'analisi
   */
  @Override
  public int requiredDetectionCount() {
    return 1;
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
    MarkableDetection detection = detections.get(0);

    double lowerControlLimit = limits.lowerLimit();
    double upperControlLimit = limits.upperLimit();

    if (detection.value() < lowerControlLimit || detection.value() > upperControlLimit) {
      detection.markOutlier();
    }
  }
}
