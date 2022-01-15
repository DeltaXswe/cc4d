package it.deltax.produlytics.uibackend.configuration;

import it.deltax.produlytics.uibackend.business.ports.ListCharacteristicsByMachineService;
import it.deltax.produlytics.uibackend.business.ports.in.ListCharacteristicsByMachineUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListCharacteristicsByMachinePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CharacteristicsUseCasesConfiguration {

    @Bean
    ListCharacteristicsByMachineUseCase listAllCharacteristicsUseCase(ListCharacteristicsByMachinePort port) {
        return new ListCharacteristicsByMachineService(port);
    }
}
