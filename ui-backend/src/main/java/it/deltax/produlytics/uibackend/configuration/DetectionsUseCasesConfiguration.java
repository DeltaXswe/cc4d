package it.deltax.produlytics.uibackend.configuration;

import it.deltax.produlytics.uibackend.business.ports.GetCharacteristicDetectionsService;
import it.deltax.produlytics.uibackend.business.ports.ListAllDetectionsAfterService;
import it.deltax.produlytics.uibackend.business.ports.in.GetCharacteristicDetectionsUseCase;
import it.deltax.produlytics.uibackend.business.ports.in.ListAllDetectionsAfterUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.FilterDetectionsCreatedAfterPort;
import it.deltax.produlytics.uibackend.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.produlytics.uibackend.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.business.ports.out.DetectionByCharacteristicPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectionsUseCasesConfiguration {

    @Bean
    GetCharacteristicDetectionsUseCase getCharacteristicTimeseriesUseCase(
            DetectionByCharacteristicPort detectionByCharacteristicPort,
            FindMachinePort findMachinePort,
            FindCharacteristicInfoPort findCharacteristicInfoPort
    ) {
        return new GetCharacteristicDetectionsService(
                detectionByCharacteristicPort,
                findMachinePort,
                findCharacteristicInfoPort
        );
    }

    @Bean
    ListAllDetectionsAfterUseCase getListAllDetectionsAfterUseCase(
            FilterDetectionsCreatedAfterPort port
    ) {
        return new ListAllDetectionsAfterService(port);
    }
}
