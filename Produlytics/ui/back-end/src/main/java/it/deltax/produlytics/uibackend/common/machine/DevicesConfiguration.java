package it.deltax.produlytics.uibackend.common.machine;

import it.deltax.produlytics.uibackend.common.machine.business.FindCharacteristicInfoService;
import it.deltax.produlytics.uibackend.common.machine.business.ListAllMachinesService;
import it.deltax.produlytics.uibackend.common.machine.business.ListCharacteristicsByMachineService;
import it.deltax.produlytics.uibackend.common.machine.business.ports.in.ListAllMachinesUseCase;
import it.deltax.produlytics.uibackend.common.machine.business.ports.in.ListCharacteristicsByMachineUseCase;
import it.deltax.produlytics.uibackend.common.machine.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.common.machine.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.common.machine.business.ports.out.ListAllMachinesPort;
import it.deltax.produlytics.uibackend.common.machine.business.ports.out.ListCharacteristicsByMachinePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicesConfiguration {

    @Bean
    ListAllMachinesUseCase listAllMachinesUseCase(ListAllMachinesPort port) {
        return new ListAllMachinesService(port);
    }

    @Bean
    ListCharacteristicsByMachineUseCase listCharacteristicsUseCase(
        ListCharacteristicsByMachinePort port
    ) {
        return new ListCharacteristicsByMachineService(port);
    }

    @Bean
    FindCharacteristicInfoService findCharacteristicInfoService(
        FindCharacteristicPort findCharacteristicPort,
        FindMachinePort findMachinePort
    ) {
        return new FindCharacteristicInfoService(findCharacteristicPort, findMachinePort);
    }

}
