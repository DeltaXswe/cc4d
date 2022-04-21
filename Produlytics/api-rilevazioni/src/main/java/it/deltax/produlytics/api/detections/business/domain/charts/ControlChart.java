package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;

/**
 * Questa interfaccia descrive l'abilità di analizzare una serie di rilevazioni per trovarne di
 * anomale.
 */
public interface ControlChart {
  /**
   * Ritorna il numero di rilevazioni richieste per l'analisi. Questo metodo deve ritornare sempre
   * lo stesso valore per una data istanza.
   *
   * @return Il numero di rilevazioni richieste per l'analisi.
   */
  int requiredDetectionCount();

  /**
   * Analizza le rilevazioni alla luce dei limiti di controllo e marca quelle anomale.
   *
   * @param detections Le rilevazioni da analizzare. Deve avere la lunghezza specificata da
   *     `requiredDetectionCount`.
   * @param limits I limiti di controllo della caratteristica a cui appartengono le rilevazioni.
   */
  void analyzeDetections(List<? extends MarkableDetection> detections, ControlLimits limits);
}
