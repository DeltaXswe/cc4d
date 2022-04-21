package it.deltax.produlytics.uibackend.devices;

import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.services.GetUnarchivedDevicesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicesConfiguration {
	@Bean
	GetUnarchivedDevicesUseCase getUnarchivedDevices(GetUnarchivedDevicesPort getUnarchivedDevicesPort){
		return new GetUnarchivedDevicesService(getUnarchivedDevicesPort);
	}
}
