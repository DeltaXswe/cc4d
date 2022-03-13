package it.deltax.produlytics.uibackend.devices;

import it.deltax.produlytics.uibackend.devices.business.FindCharacteristicInfoService;
import it.deltax.produlytics.uibackend.devices.business.ListAllDevicesService;
import it.deltax.produlytics.uibackend.devices.business.GetUnarchivedCharacteristicsService;
import it.deltax.produlytics.uibackend.devices.business.ports.in.ListAllDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.ListAllDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicesConfiguration {

    @Bean
	ListAllDevicesUseCase listAllDevicesUseCase(ListAllDevicesPort port) {
        return new ListAllDevicesService(port);
    }

    @Bean
	GetUnarchivedCharacteristicsUseCase listCharacteristicsUseCase(
        FindAllUnarchivedCharacteristicPort port
    ) {
        return new GetUnarchivedCharacteristicsService(port);
    }

    @Bean
	FindCharacteristicInfoService findCharacteristicInfoService(
        FindCharacteristicPort findCharacteristicPort,
        FindMachinePort findMachinePort
    ) {
        return new FindCharacteristicInfoService(findCharacteristicPort, findMachinePort);
    }

}
