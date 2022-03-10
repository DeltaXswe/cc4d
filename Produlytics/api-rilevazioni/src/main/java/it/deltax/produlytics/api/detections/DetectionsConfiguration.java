package it.deltax.produlytics.api.detections;

import it.deltax.produlytics.api.detections.business.domain.cache.DetectionCacheFactory;
import it.deltax.produlytics.api.detections.business.domain.cache.DetectionCacheFactoryImpl;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart7SameOrder;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlCharts;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueueImpl;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidatorImpl;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.detections.business.ports.out.FindValidationInfoPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;
import it.deltax.produlytics.api.detections.business.ports.services.DetectionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DetectionsConfiguration {
	@Bean
	ProcessIncomingDetectionUseCase createProcessDetectionUseCase(FindValidationInfoPort findCharacteristicInfoPort,
		FindLastDetectionsPort findLastDetectionsPort,
		InsertDetectionPort insertDetectionPort,
		MarkOutlierPort markOutlierPort)
	{
		DetectionValidator detectionValidation = new DetectionValidatorImpl(findCharacteristicInfoPort);

		DetectionCacheFactory detectionCacheFactory = new DetectionCacheFactoryImpl(findLastDetectionsPort,
			insertDetectionPort,
			markOutlierPort);
		ControlChart controlCharts = new ControlCharts(List.of(new ControlChart7SameOrder() /* TODO: altre carte di controllo */));
		DetectionQueue detectionQueue = new DetectionQueueImpl(detectionCacheFactory, controlCharts);

		return new DetectionsService(detectionValidation, detectionQueue);
	}
}
