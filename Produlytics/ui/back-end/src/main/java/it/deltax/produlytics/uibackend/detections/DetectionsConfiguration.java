package it.deltax.produlytics.uibackend.detections;

import it.deltax.produlytics.uibackend.detections.business.ports.ListDetectionsByCharacteristicService;
import it.deltax.produlytics.uibackend.detections.business.ports.in.ListDetectionsByCharacteristicUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectionsConfiguration {

    @Bean
	ListDetectionsByCharacteristicUseCase getListDetectionsByCharacteristicUseCase(
            FindAllDetectionsPort port
    ) {
        return new ListDetectionsByCharacteristicService(port);
    }
}
