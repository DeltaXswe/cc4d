package it.deltax.produlytics.api.detections.business.domain.series;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.series.facade.SeriesPortFacade;
import lombok.AllArgsConstructor;

/** Questa classe implementa una rilevazione che può essere marcata come anomala. */
@AllArgsConstructor
public class MarkableDetectionAdapter implements MarkableDetection {
  /** La porta utilizzata per marcare le rilevazioni come anomale. */
  private final SeriesPortFacade seriesPortFacade;
  /** La rilevazione. */
  private final Detection detection;

  /**
   * Questo metodo implementa l'omonimo definito in {@code MarkableDetection}.
   *
   * @return il valore della rilevazione
   */
  @Override
  public double value() {
    return this.detection.value();
  }

  /** Questo metodo implementa l'omonimo definito in {@code MarkableDetection}. */
  @Override
  public void markOutlier() {
    this.seriesPortFacade.markOutlier(this.detection);
  }
}
