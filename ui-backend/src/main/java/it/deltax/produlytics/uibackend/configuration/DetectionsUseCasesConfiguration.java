package it.deltax.produlytics.uibackend.configuration;

import it.deltax.produlytics.uibackend.business.ports.ListDetectionsService;
import it.deltax.produlytics.uibackend.business.ports.in.ListDetectionsUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListDetectionsPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectionsUseCasesConfiguration {

    @Bean
    ListDetectionsUseCase getListAllDetectionsAfterUseCase(
            ListDetectionsPort port
    ) {
        return new ListDetectionsService(port);
    }
}
