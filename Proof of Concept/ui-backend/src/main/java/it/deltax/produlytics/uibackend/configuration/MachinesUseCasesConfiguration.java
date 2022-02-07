package it.deltax.produlytics.uibackend.configuration;

import it.deltax.produlytics.uibackend.business.ports.ListAllMachinesService;
import it.deltax.produlytics.uibackend.business.ports.in.ListAllMachinesUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListAllMachinesPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MachinesUseCasesConfiguration {

    @Bean
    ListAllMachinesUseCase listAllMachinesUseCase(ListAllMachinesPort port) {
        return new ListAllMachinesService(port);
    }
}
