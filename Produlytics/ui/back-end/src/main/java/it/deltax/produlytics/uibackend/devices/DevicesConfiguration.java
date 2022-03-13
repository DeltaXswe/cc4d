package it.deltax.produlytics.uibackend.devices;

import it.deltax.produlytics.uibackend.devices.business.FindCharacteristicInfoService;
import it.deltax.produlytics.uibackend.devices.business.ListAllDevicesService;
import it.deltax.produlytics.uibackend.devices.business.ListCharacteristicsByDeviceService;
import it.deltax.produlytics.uibackend.devices.business.ports.in.ListAllDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.ListCharacteristicsByDeviceUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.ListAllDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.ListCharacteristicsByDevicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicesConfiguration {

    @Bean
	ListAllDevicesUseCase listAllDevicesUseCase(ListAllDevicesPort port) {
        return new ListAllDevicesService(port);
    }

    @Bean
	ListCharacteristicsByDeviceUseCase listCharacteristicsUseCase(
        ListCharacteristicsByDevicePort port
    ) {
        return new ListCharacteristicsByDeviceService(port);
    }

    @Bean
	FindCharacteristicInfoService findCharacteristicInfoService(
        FindCharacteristicPort findCharacteristicPort,
        FindMachinePort findMachinePort
    ) {
        return new FindCharacteristicInfoService(findCharacteristicPort, findMachinePort);
    }

}
