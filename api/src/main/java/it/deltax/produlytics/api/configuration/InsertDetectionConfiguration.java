package it.deltax.produlytics.api.configuration;

import it.deltax.produlytics.api.business.ports.InsertDetectionService;
import it.deltax.produlytics.api.business.ports.in.InsertDetectionUseCase;
import it.deltax.produlytics.api.business.ports.out.InsertDetectionPort;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertDetectionConfiguration {

    @Bean
    public InsertDetectionUseCase createInsertDetectionUseCase(
        InsertDetectionPort inseriDetectionPort
    ) {
        return new InsertDetectionService(inseriDetectionPort);
    }

}
