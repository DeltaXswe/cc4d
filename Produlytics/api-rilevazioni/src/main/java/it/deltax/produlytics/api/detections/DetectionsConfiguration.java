package it.deltax.produlytics.api.detections;

import it.deltax.produlytics.api.detections.business.domain.charts.*;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlCharts;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartsImpl;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.*;
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
import it.deltax.produlytics.api.detections.business.ports.out.*;
import it.deltax.produlytics.api.detections.business.ports.services.DetectionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

// Dependency Injection manuale visto che le classi di business non dovrebbero dipendere da quella di Spring.
@Component
@SuppressWarnings("unused") // Viene utilizzata da Spring a runtime, ma l'analisi statica non lo può sapere
public class DetectionsConfiguration {
	@Bean
	DetectionValidator createDetectionValidator(
		FindDeviceByApiKeyPort findDeviceByApiKeyPort, FindCharacteristicPort findCharacteristicPort
	) {
		return new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicPort);
	}

	@Bean
	ControlCharts createControlCharts() {
		List<ControlChart> controlChartList = List.of(
			new ControlChartBeyondLimits(),
			new ControlChartZoneA(),
			new ControlChartZoneB(),
			new ControlChartZoneC(),
			new ControlChartTrend(),
			new ControlChartMixture(),
			new ControlChartStratification(),
			new ControlChartOverControl()
		);

		return new ControlChartsImpl(controlChartList);
	}

	@Bean
	SeriePortFacade createSerieFacadePort(
		InsertDetectionPort insertDetectionPort,
		FindLastDetectionsPort findLastDetectionsPort,
		MarkOutlierPort markOutlierPort
	) {
		return new SeriePortFacadeImpl(insertDetectionPort, findLastDetectionsPort, markOutlierPort);
	}

	@Bean
	ControlLimitsCalculator controlLimitsCalculator(FindLimitsPort findLimitsPort) {
		return new ControlLimitsCalculatorImpl(findLimitsPort);
	}

	@Bean
	DetectionSerieFactory createDetectionSerieFactory(
		SeriePortFacade seriePortFacade, ControlLimitsCalculator controlLimitsCalculator, ControlCharts controlCharts
	) {
		return new DetectionSerieImplFactory(seriePortFacade, controlLimitsCalculator, controlCharts);
	}

	@Bean
	@Scope("singleton")
	DetectionQueue createDetectionQueue(DetectionSerieFactory detectionSerieFactory) {
		return new DetectionQueueImpl(30, detectionSerieFactory);
	}

	@Bean
	ProcessIncomingDetectionUseCase createProcessDetectionUseCase(
		DetectionValidator detectionValidation, DetectionQueue detectionQueue
	) {
		return new DetectionsService(detectionValidation, detectionQueue);
	}
}
