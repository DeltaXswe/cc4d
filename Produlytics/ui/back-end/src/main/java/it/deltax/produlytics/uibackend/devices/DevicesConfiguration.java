package it.deltax.produlytics.uibackend.devices;

import it.deltax.produlytics.uibackend.devices.business.FindCharacteristicInfoService;
import it.deltax.produlytics.uibackend.devices.business.GetUnarchivedDevicesService;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindTinyDevicePort;
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
	FindCharacteristicInfoService findCharacteristicInfoService(
        FindCharacteristicPort findCharacteristicPort,
        FindTinyDevicePort findDevicePort
    ) {
        return new FindCharacteristicInfoService(findCharacteristicPort, findDevicePort);
    }

}
