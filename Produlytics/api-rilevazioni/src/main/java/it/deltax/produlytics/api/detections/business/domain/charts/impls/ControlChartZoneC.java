package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

/**
 * Questa classe implementa la carta di controllo descritta in ROF24.4, cioè identifica se 7 punti
 * consecutivi sono dallo stesso lato rispetto alla media.
 */
public class ControlChartZoneC implements ControlChart {
  /**
   * Implementazione dell'omonimo metodo definito in `ControlChart`.
   *
   * @return Il numero di rilevazioni richieste per l'analisi.
   */
  @Override
  public int requiredDetectionCount() {
    return 7;
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
    double mean = limits.mean();
    long inLowerZone = detections.stream().filter(detection -> detection.value() < mean).count();
    long inUpperZone = detections.stream().filter(detection -> detection.value() > mean).count();

    if (inLowerZone == 7 || inUpperZone == 7) {
      ControlChartUtils.markAll(detections);
    }
  }
}
