package it.deltax.produlytics.uibackend.configuration;

import it.deltax.produlytics.uibackend.business.ports.TimeSeriesService;
import it.deltax.produlytics.uibackend.business.ports.in.TimeSeriesUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.produlytics.uibackend.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.business.ports.out.ListDetectionsPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeSeriesUseCaseConfiguration {
    @Bean
    TimeSeriesUseCase getTimeSeriesUseCase(
            FindCharacteristicInfoPort findCharacteristicInfoPort,
            FindMachinePort findMachinePort,
            ListDetectionsPort listDetectionsPort
    ) {
        return new TimeSeriesService(findCharacteristicInfoPort, findMachinePort, listDetectionsPort);
    }
}
