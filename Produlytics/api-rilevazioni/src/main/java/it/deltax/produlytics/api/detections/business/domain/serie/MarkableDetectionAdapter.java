package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;
import lombok.AllArgsConstructor;

/** Questa classe implementa una rilevazione che può essere marcata come anomala. */
@AllArgsConstructor
public class MarkableDetectionAdapter implements MarkableDetection {
  /** La porta utilizzata per marcare le rilevazioni come anomale. */
  private final SeriePortFacade seriePortFacade;
  /** La rilevazione. */
  private final Detection detection;

  /**
   * Questo metodo implementa l'omonimo definito in `MarkableDetection`.
   *
   * @return Il valore della rilevazione;
   */
  @Override
  public double value() {
    return this.detection.value();
  }

  /** Questo metodo implementa l'omonimo definito in `MarkableDetection`. */
  @Override
  public void markOutlier() {
    this.seriePortFacade.markOutlier(this.detection);
  }
}
