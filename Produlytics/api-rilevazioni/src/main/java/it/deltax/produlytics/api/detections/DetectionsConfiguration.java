package it.deltax.produlytics.api.detections;

import it.deltax.produlytics.api.detections.business.domain.cache.CachedDetectionSerieFactory;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart7SameOrder;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlCharts;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueueImpl;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionSerieFactory;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidatorImpl;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.detections.business.ports.out.*;
import it.deltax.produlytics.api.detections.business.ports.services.DetectionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DetectionsConfiguration {
	@Bean
	ProcessIncomingDetectionUseCase createProcessDetectionUseCase(
		FindDeviceValidationByApiKeyPort findDeviceValidationByApiKeyPort,
		FindCharacteristicValidationPort findCharacteristicValidationPort,
		FindLastDetectionsPort findLastDetectionsPort,
		InsertDetectionPort insertDetectionPort,
		MarkOutlierPort markOutlierPort
	) {
		DetectionValidator detectionValidation = new DetectionValidatorImpl(findDeviceValidationByApiKeyPort,
			findCharacteristicValidationPort
		);

		DetectionSerieFactory detectionSerieFactory = new CachedDetectionSerieFactory(findLastDetectionsPort,
			insertDetectionPort,
			markOutlierPort
		);
		ControlChart controlCharts = new ControlCharts(List.of(new ControlChart7SameOrder() /* TODO: altre carte di controllo */));
		DetectionQueue detectionQueue = new DetectionQueueImpl(detectionSerieFactory, controlCharts);

		return new DetectionsService(detectionValidation, detectionQueue);
	}
}
