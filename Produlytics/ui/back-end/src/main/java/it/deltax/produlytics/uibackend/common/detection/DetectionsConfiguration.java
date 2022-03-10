package it.deltax.produlytics.uibackend.common.detection;

import it.deltax.produlytics.uibackend.common.detection.business.ports.ListDetectionsByCharacteristicService;
import it.deltax.produlytics.uibackend.common.detection.business.ports.in.ListDetectionsByCharacteristicUseCase;
import it.deltax.produlytics.uibackend.common.detection.business.ports.out.ListDetectionsByCharacteristicPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectionsConfiguration {

    @Bean
	ListDetectionsByCharacteristicUseCase getListDetectionsByCharacteristicUseCase(
            ListDetectionsByCharacteristicPort port
    ) {
        return new ListDetectionsByCharacteristicService(port);
    }
}
