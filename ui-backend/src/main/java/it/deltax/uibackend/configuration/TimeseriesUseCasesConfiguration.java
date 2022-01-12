package it.deltax.uibackend.configuration;

import it.deltax.uibackend.business.ports.GetCharacteristicTimeseriesService;
import it.deltax.uibackend.business.ports.in.GetCharacteristicTimeseriesUseCase;
import it.deltax.uibackend.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.uibackend.business.ports.out.FindMachinePort;
import it.deltax.uibackend.business.ports.out.TimeseriesPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeseriesUseCasesConfiguration {

    @Bean
    GetCharacteristicTimeseriesUseCase getCharacteristicTimeseriesUseCase(
            TimeseriesPort timeseriesPort,
            FindMachinePort findMachinePort,
            FindCharacteristicInfoPort findCharacteristicInfoPort
    ) {
        return new GetCharacteristicTimeseriesService(
                timeseriesPort,
                findMachinePort,
                findCharacteristicInfoPort
        );
    }
}
