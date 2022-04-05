package it.deltax.produlytics.uibackend.detections;

import it.deltax.produlytics.uibackend.detections.business.ports.GetDetectionsService;
import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectionsConfiguration {

    @Bean
	GetDetectionsUseCase getListDetectionsByCharacteristicUseCase(
            FindAllDetectionsPort port
    ) {
        return new GetDetectionsService(port);
    }
}
