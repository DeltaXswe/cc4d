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
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerieFactory;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerieImplFactory;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacadeImpl;
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
   * Questo metodo crea un'istanza di `DetectionValidator`.
   *
   * @param findDeviceByApiKeyPort La `FindDeviceByApiKeyPort` da usare nel costruttore di
   *     `DetectionValidatorImpl`.
   * @param findCharacteristicByNamePort La `FindCharacteristicByNamePort` da usare nel costruttore
   *     di `DetectionValidatorImpl`.
   * @return Una nuova istanza di `DetectionValidator`.
   */
  @Bean
  DetectionValidator createDetectionValidator(
      FindDeviceByApiKeyPort findDeviceByApiKeyPort,
      FindCharacteristicByNamePort findCharacteristicByNamePort) {
    return new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicByNamePort);
  }

  /**
   * Questo metodo crea un'istanza di `ControlChartsGroup`.
   *
   * @return Una nuova istanza di `ControlChartsGroup`.
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
   * Questo metodo crea un'istanza di `SeriePortFacade`.
   *
   * @param insertDetectionPort La `InsertDetectionPort` da usare nel costruttore di
   *     `SeriePortFacadeImpl`.
   * @param findLastDetectionsPort La `FindLastDetectionsPort` da usare nel costruttore di
   *     `SeriePortFacadeImpl`.
   * @param markOutlierPort La `MarkOutlierPort` da usare nel costruttore di `SeriePortFacadeImpl`.
   * @return Una nuova istanza di `SeriePortFacade`.
   */
  @Bean
  SeriePortFacade createSerieFacadePort(
      InsertDetectionPort insertDetectionPort,
      FindLastDetectionsPort findLastDetectionsPort,
      MarkOutlierPort markOutlierPort) {
    return new SeriePortFacadeImpl(insertDetectionPort, findLastDetectionsPort, markOutlierPort);
  }

  /**
   * Questo metodo crea un'istanza di `ControlLimitsCalculator`.
   *
   * @param findLimitsPort La `FindLimitsPort` da usare nel costruttore di
   *     `ControlLimitsCalculatorImpl`.
   * @return Una nuova istanza di `ControlLimitsCalculator`.
   */
  @Bean
  ControlLimitsCalculator controlLimitsCalculator(FindLimitsPort findLimitsPort) {
    return new ControlLimitsCalculatorImpl(findLimitsPort);
  }

  /**
   * Questo metodo crea un'istanza di `DetectionSerieFactory`.
   *
   * @param seriePortFacade La `SeriePortFacade` da usare nel costruttore di
   *     `DetectionSerieImplFactory`.
   * @param controlLimitsCalculator Il `ControlLimitsCalculator` da usare nel costruttore di
   *     `DetectionSerieImplFactory`.
   * @param controlChartsGroup Il `ControlChartsGroup` da usare nel costruttore di
   *     `DetectionSerieImplFactory`.
   * @return Una nuova istanza di `DetectionSerieFactory`.
   */
  @Bean
  DetectionSerieFactory createDetectionSerieFactory(
      SeriePortFacade seriePortFacade,
      ControlLimitsCalculator controlLimitsCalculator,
      ControlChartsGroup controlChartsGroup) {
    return new DetectionSerieImplFactory(
        seriePortFacade, controlLimitsCalculator, controlChartsGroup);
  }

  /**
   * Questo metodo crea un'istanza di `DetectionQueue`.
   *
   * @param detectionSerieFactory La `DetectionSerieFactory` da usare nel costruttore di
   *     `DetectionQueueImpl`.
   * @return Una nuova istanza di `DetectionQueue`.
   */
  @Bean
  @Scope("singleton")
  DetectionQueue createDetectionQueue(DetectionSerieFactory detectionSerieFactory) {
    return new DetectionQueueImpl(detectionSerieFactory);
  }

  /**
   * Questo metodo crea un'istanza di `ProcessIncomingDetectionUseCase`.
   *
   * @param detectionValidation Il `DetectionValidator` da usare nel costruttore di
   *     `DetectionsService`.
   * @param detectionQueue La `DetectionQueue` da usare nel costruttore di `DetectionsService`.
   * @return Una nuova istanza di `ProcessIncomingDetectionUseCase`.
   */
  @Bean
  ProcessIncomingDetectionUseCase createProcessDetectionUseCase(
      DetectionValidator detectionValidation, DetectionQueue detectionQueue) {
    return new DetectionsService(detectionValidation, detectionQueue);
  }
}
