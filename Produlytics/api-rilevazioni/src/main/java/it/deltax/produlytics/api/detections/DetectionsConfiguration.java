package it.deltax.produlytics.api.detections;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartsGroup;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartsGroupImpl;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartBeyondLimits;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartMixture;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartOverControl;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartStratification;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartTrend;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartZoneA;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartZoneB;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartZoneC;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculatorImpl;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueueImpl;
import it.deltax.produlytics.api.detections.business.domain.series.DetectionSeriesFactory;
import it.deltax.produlytics.api.detections.business.domain.series.DetectionSeriesImplFactory;
import it.deltax.produlytics.api.detections.business.domain.series.facade.SeriesPortFacade;
import it.deltax.produlytics.api.detections.business.domain.series.facade.SeriesPortFacadeImpl;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidatorImpl;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicByNamePort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceByApiKeyPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindLimitsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;
import it.deltax.produlytics.api.detections.business.services.DetectionsService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Questa classe implementa una configurazione di Spring che descrive come creare le varie classi di
 * business.
 */
@Component
@SuppressWarnings(
    "unused") // Viene utilizzata da Spring a runtime, ma l'analisi statica non lo può sapere
public class DetectionsConfiguration {
  /**
   * Questo metodo crea un'istanza di {@code DetectionValidator}.
   *
   * @param findDeviceByApiKeyPort la {@code FindDeviceByApiKeyPort} da usare nel costruttore di
   *     {@code DetectionValidatorImpl}
   * @param findCharacteristicByNamePort la {@code FindCharacteristicByNamePort} da usare nel costruttore
   *     di {@code DetectionValidatorImpl}
   * @return una nuova istanza di {@code DetectionValidator}
   */
  @Bean
  DetectionValidator createDetectionValidator(
      FindDeviceByApiKeyPort findDeviceByApiKeyPort,
      FindCharacteristicByNamePort findCharacteristicByNamePort) {
    return new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicByNamePort);
  }

  /**
   * Questo metodo crea un'istanza di {@code ControlChartsGroup}.
   *
   * @return una nuova istanza di {@code ControlChartsGroup}
   */
  @Bean
  ControlChartsGroup createControlCharts() {
    List<ControlChart> controlChartList =
        List.of(
            new ControlChartBeyondLimits(),
            new ControlChartZoneA(),
            new ControlChartZoneB(),
            new ControlChartZoneC(),
            new ControlChartTrend(),
            new ControlChartMixture(),
            new ControlChartStratification(),
            new ControlChartOverControl());

    return new ControlChartsGroupImpl(controlChartList);
  }

  /**
   * Questo metodo crea un'istanza di {@code SeriesPortFacade}.
   *
   * @param insertDetectionPort la {@code InsertDetectionPort} da usare nel costruttore di
   *     {@code SeriesPortFacadeImpl}
   * @param findLastDetectionsPort la {@code FindLastDetectionsPort} da usare nel costruttore di
   *     {@code SeriesPortFacadeImpl}
   * @param markOutlierPort la {@code MarkOutlierPort} da usare nel costruttore di {@code SeriesPortFacadeImpl}
   * @return una nuova istanza di {@code SeriesPortFacade}
   */
  @Bean
  SeriesPortFacade createSeriesFacadePort(
      InsertDetectionPort insertDetectionPort,
      FindLastDetectionsPort findLastDetectionsPort,
      MarkOutlierPort markOutlierPort) {
    return new SeriesPortFacadeImpl(insertDetectionPort, findLastDetectionsPort, markOutlierPort);
  }

  /**
   * Questo metodo crea un'istanza di {@code ControlLimitsCalculator}.
   *
   * @param findLimitsPort la {@code FindLimitsPort} da usare nel costruttore di
   *     {@code ControlLimitsCalculatorImpl}
   * @return una nuova istanza di {@code ControlLimitsCalculator}
   */
  @Bean
  ControlLimitsCalculator controlLimitsCalculator(FindLimitsPort findLimitsPort) {
    return new ControlLimitsCalculatorImpl(findLimitsPort);
  }

  /**
   * Questo metodo crea un'istanza di {@code DetectionSeriesFactory}.
   *
   * @param seriesPortFacade la {@code SeriesPortFacade} da usare nel costruttore di
   *     {@code DetectionSeriesImplFactory}
   * @param controlLimitsCalculator il {@code ControlLimitsCalculator} da usare nel costruttore di
   *     {@code DetectionSeriesImplFactory}
   * @param controlChartsGroup il {@code ControlChartsGroup} da usare nel costruttore di
   *     {@code DetectionSeriesImplFactory}
   * @return una nuova istanza di {@code DetectionSeriesFactory}
   */
  @Bean
  DetectionSeriesFactory createDetectionSeriesFactory(
      SeriesPortFacade seriesPortFacade,
      ControlLimitsCalculator controlLimitsCalculator,
      ControlChartsGroup controlChartsGroup) {
    return new DetectionSeriesImplFactory(
        seriesPortFacade, controlLimitsCalculator, controlChartsGroup);
  }

  /**
   * Questo metodo crea un'istanza di {@code DetectionQueue}.
   *
   * @param detectionSeriesFactory la {@code DetectionSeriesFactory} da usare nel costruttore di
   *     {@code DetectionQueueImpl}
   * @return una nuova istanza di {@code DetectionQueue}
   */
  @Bean
  @Scope("singleton")
  DetectionQueue createDetectionQueue(DetectionSeriesFactory detectionSeriesFactory) {
    return new DetectionQueueImpl(detectionSeriesFactory);
  }

  /**
   * Questo metodo crea un'istanza di {@code ProcessIncomingDetectionUseCase}.
   *
   * @param detectionValidation il {@code DetectionValidator} da usare nel costruttore di
   *     {@code DetectionsService}
   * @param detectionQueue la {@code DetectionQueue} da usare nel costruttore di {@code DetectionsService}
   * @return una nuova istanza di {@code ProcessIncomingDetectionUseCase}
   */
  @Bean
  ProcessIncomingDetectionUseCase createProcessDetectionUseCase(
      DetectionValidator detectionValidation, DetectionQueue detectionQueue) {
    return new DetectionsService(detectionValidation, detectionQueue);
  }
}
