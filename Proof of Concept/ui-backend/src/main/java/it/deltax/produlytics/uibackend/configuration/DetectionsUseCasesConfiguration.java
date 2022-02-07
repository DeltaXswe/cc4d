package it.deltax.produlytics.uibackend.configuration;

import it.deltax.produlytics.uibackend.business.ports.ListDetectionsByCharacteristicService;
import it.deltax.produlytics.uibackend.business.ports.in.ListDetectionsByCharacteristicUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListDetectionsByCharacteristicPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectionsUseCasesConfiguration {

    @Bean
    ListDetectionsByCharacteristicUseCase getListDetectionsByCharacteristicUseCase(
            ListDetectionsByCharacteristicPort port
    ) {
        return new ListDetectionsByCharacteristicService(port);
    }
}
