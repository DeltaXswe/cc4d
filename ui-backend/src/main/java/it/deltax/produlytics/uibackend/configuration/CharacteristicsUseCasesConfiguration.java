package it.deltax.produlytics.uibackend.configuration;

import it.deltax.produlytics.uibackend.business.ports.ListAllCharacteristicsService;
import it.deltax.produlytics.uibackend.business.ports.in.ListAllCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListAllCharacteristicsPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CharacteristicsUseCasesConfiguration {

    @Bean
    ListAllCharacteristicsUseCase listAllCharacteristicsUseCase(ListAllCharacteristicsPort port) {
        return new ListAllCharacteristicsService(port);
    }
}
