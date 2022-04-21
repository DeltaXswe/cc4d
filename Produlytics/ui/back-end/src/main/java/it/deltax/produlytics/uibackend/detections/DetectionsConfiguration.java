package it.deltax.produlytics.uibackend.detections;

import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.detections.business.services.GetDetectionsService;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectionsConfiguration {
	@Bean
	GetDetectionsUseCase getDetectionsUseCase(FindAllDetectionsPort findDetectionsPort,
		FindCharacteristicLimitsPort findCharacteristicPort){
		return new GetDetectionsService(findDetectionsPort, findCharacteristicPort);
	}
}
