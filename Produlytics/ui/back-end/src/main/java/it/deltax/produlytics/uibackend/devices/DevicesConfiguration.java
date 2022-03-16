package it.deltax.produlytics.uibackend.devices;

import it.deltax.produlytics.uibackend.devices.business.FindCharacteristicInfoService;
import it.deltax.produlytics.uibackend.devices.business.GetUnarchivedDevicesService;
import it.deltax.produlytics.uibackend.devices.business.GetUnarchivedCharacteristicsService;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindDevicePort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicesConfiguration {

    @Bean
    GetUnarchivedDevicesUseCase getUnarchivedDevicesUseCase(
            FindAllUnarchivedDevicesPort port
    ) {
        return new GetUnarchivedDevicesService(port);
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
        FindDevicePort findDevicePort
    ) {
        return new FindCharacteristicInfoService(findCharacteristicPort, findDevicePort);
    }

}
