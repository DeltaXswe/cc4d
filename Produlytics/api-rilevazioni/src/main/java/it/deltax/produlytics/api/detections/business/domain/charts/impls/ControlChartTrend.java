package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;

/**
 * Questa classe implementa la carta di controllo che identifica
 * se 7 punti consecutivi seguono lo stesso ordine.
 */
public class ControlChartTrend implements ControlChart {
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
    // Per ogni tripletta di punti consecutivi w0, w1 e w2 controlla se sono tutti nello stesso
    // ordine.
    // Poichè le triplette si sovrappongono il trend deve continuare allo stesso modo in tutta la
    // sequenza
    // per poter risultare true alla fine.
    boolean sameTrend =
        ControlChartUtils.windows(detections, 3)
            .allMatch(
                window -> {
                  double w0 = window.get(0).value();
                  double w1 = window.get(1).value();
                  double w2 = window.get(2).value();
                  boolean alwaysInc = (w0 < w1) && (w1 < w2);
                  boolean alwaysDec = (w0 > w1) && (w1 > w2);
                  return alwaysInc || alwaysDec;
                });

    if (sameTrend) {
      ControlChartUtils.markAll(detections);
    }
  }
}
