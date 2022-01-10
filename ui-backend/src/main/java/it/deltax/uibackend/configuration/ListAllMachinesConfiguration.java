package it.deltax.uibackend.configuration;

import it.deltax.uibackend.business.ports.ListAllMachinesService;
import it.deltax.uibackend.business.ports.in.ListAllMachinesUseCase;
import it.deltax.uibackend.business.ports.out.ListAllMachinesPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListAllMachinesConfiguration {

    @Bean
    ListAllMachinesUseCase listAllMachinesUseCase(ListAllMachinesPort port) {
        return new ListAllMachinesService(port);
    }
}
