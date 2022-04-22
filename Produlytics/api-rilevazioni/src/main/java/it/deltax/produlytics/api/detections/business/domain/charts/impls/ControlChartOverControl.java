package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;

/**
 * Questa classe implementa la carta di controllo descritta in ROF22.8, cioè identifica se 14 punti
 * consecutivi sono a zig-zag.
 */
public class ControlChartOverControl implements ControlChart {
  /**
   * Implementazione dell'omonimo metodo definito in `ControlChart`.
   *
   * @return Il numero di rilevazioni richieste per l'analisi.
   */
  @Override
  public int requiredDetectionCount() {
    return 14;
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
    // Per ogni tripletta di punti consecutivi w0, w1 e w2 controlla se formino una forma a V o V
    // rovesciata.
    // Poichè le triplette si sovrappongono l'alternamento deve continuare allo stesso modo in tutta
    // la sequenza
    // per poter risultare true alla fine.
    boolean isOverControl =
        ControlChartUtils.windows(detections, 3)
            .allMatch(
                window -> {
                  double w0 = window.get(0).value();
                  double w1 = window.get(1).value();
                  double w2 = window.get(2).value();
                  boolean incDec = (w0 < w1) && (w1 > w2);
                  boolean decInc = (w0 > w1) && (w1 < w2);
                  return incDec || decInc;
                });
    if (isOverControl) {
      ControlChartUtils.markAll(detections);
    }
  }
}
