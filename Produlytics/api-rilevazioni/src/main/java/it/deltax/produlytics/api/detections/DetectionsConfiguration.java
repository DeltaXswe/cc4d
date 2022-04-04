package it.deltax.produlytics.api.detections;

import it.deltax.produlytics.api.detections.business.domain.control_chart.*;
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
		FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort, FindCharacteristicInfoPort findCharacteristicInfoPort
	) {
		return new DetectionValidatorImpl(findDeviceInfoByApiKeyPort, findCharacteristicInfoPort);
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
		FindLimitsPort findLimitsPort,
		FindLastDetectionsPort findLastDetectionsPort,
		MarkOutlierPort markOutlierPort
	) {
		return new SeriePortFacadeImpl(insertDetectionPort, findLimitsPort, findLastDetectionsPort, markOutlierPort);
	}

	@Bean
	DetectionSerieFactory createDetectionSerieFactory(ControlCharts controlCharts, SeriePortFacade seriePortFacade) {
		return new DetectionSerieImplFactory(seriePortFacade, controlCharts);
	}

	@Bean
	@Scope("singleton")
	DetectionQueue createDetectionQueue(DetectionSerieFactory detectionSerieFactory) {
		return new DetectionQueueImpl(detectionSerieFactory);
	}

	@Bean
	ProcessIncomingDetectionUseCase createProcessDetectionUseCase(
		DetectionValidator detectionValidation, DetectionQueue detectionQueue
	) {
		return new DetectionsService(detectionValidation, detectionQueue);
	}
}
