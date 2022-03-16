package it.deltax.produlytics.api.detections;

import it.deltax.produlytics.api.detections.business.domain.control_chart.*;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueueImpl;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerieFactory;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerieImplFactory;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidatorImpl;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.detections.business.ports.out.*;
import it.deltax.produlytics.api.detections.business.ports.services.DetectionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("unused")
public class DetectionsConfiguration {
	@Bean
	ProcessIncomingDetectionUseCase createProcessDetectionUseCase(
		FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort,
		FindCharacteristicInfoPort findCharacteristicInfoPort,
		InsertDetectionPort insertDetectionPort,
		FindLimitsPort findLimitsPort,
		FindLastDetectionsPort findLastDetectionsPort
	) {
		DetectionValidator detectionValidation = new DetectionValidatorImpl(findDeviceInfoByApiKeyPort,
			findCharacteristicInfoPort
		);

		List<ControlChart> controlCharts = List.of(new ControlChartBeyondLimits(),
			new ControlChartZoneA(),
			new ControlChartZoneB(),
			new ControlChartZoneC(),
			new ControlChartTrend(),
			new ControlChartMixture(),
			new ControlChartStratification(),
			new ControlChartOverControl()
		);

		DetectionSerieFactory detectionSerieFactory = new DetectionSerieImplFactory(insertDetectionPort,
			findLimitsPort,
			findLastDetectionsPort,
			controlCharts
		);

		DetectionQueue detectionQueue = new DetectionQueueImpl(detectionSerieFactory);

		return new DetectionsService(detectionValidation, detectionQueue);
	}
}
