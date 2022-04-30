package it.deltax.produlytics.api.detections.business.domain.series;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartsGroup;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.series.facade.SeriesPortFacade;
import lombok.AllArgsConstructor;

/** Questa classe si occupa di creare una {@code DetectionSeriesImpl}. */
@AllArgsConstructor
public class DetectionSeriesImplFactory implements DetectionSeriesFactory {
  /** Le porte utilizzate da {@code DetectionSeriesImpl}. */
  private final SeriesPortFacade ports;
  /** Un calcolatore di limiti di controllo utilizzato dalla {@code DetectionSeriesImpl}. */
  private final ControlLimitsCalculator controlLimitsCalculator;
  /** Le carte di controllo che la {@code DetectionSeriesImpl} applicherà. */
  private final ControlChartsGroup controlChartsGroup;

  /**
   * Questo metodo implementa l'omonimo metodo definito in {@code DetectionSeriesFactory}, creando una
   * {@code DetectionSeriesImpl}.
   *
   * @param characteristicId l'identificativo globale della caratteristica a cui sarà associata la
   *     {@code DetectionSeries}
   * @return una nuova istanza di {@code DetectionSeriesImpl}
   */
  @Override
  public DetectionSeries createSeries(CharacteristicId characteristicId) {
    return new DetectionSeriesImpl(
        this.ports, this.controlLimitsCalculator, this.controlChartsGroup, characteristicId);
  }
}
