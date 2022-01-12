package it.deltax.uibackend.configuration;

import it.deltax.uibackend.business.ports.ListAllCharacteristicsService;
import it.deltax.uibackend.business.ports.in.ListAllCharacteristicsUseCase;
import it.deltax.uibackend.business.ports.out.ListAllCharacteristicsPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListAllCharacteristicsConfiguration {

    @Bean
    ListAllCharacteristicsUseCase listAllCharacteristicsUseCase(ListAllCharacteristicsPort port) {
        return new ListAllCharacteristicsService(port);
    }
}
