package it.deltax.produlytics.uibackend.devices;

import it.deltax.produlytics.uibackend.devices.business.ports.in.GetLimitsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.services.GetLimitsService;
import it.deltax.produlytics.uibackend.devices.business.services.GetUnarchivedCharacteristicsService;
import it.deltax.produlytics.uibackend.devices.business.services.GetUnarchivedDevicesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicesConfiguration {
	@Bean
	GetLimitsUseCase getLimitsUseCase(
		GetUnarchivedDevicesPort findDevicesPort,
		FindCharacteristicLimitsPort findCharacteristicLimitsPort
	){
		return new GetLimitsService(findDevicesPort, findCharacteristicLimitsPort);
	}

	@Bean
	GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristicsUseCase(
		GetUnarchivedDevicesPort findDevicesPort,
		FindAllUnarchivedCharacteristicsPort findCharacteristicsPort
	){
		return new GetUnarchivedCharacteristicsService(findDevicesPort, findCharacteristicsPort);
	}

	@Bean
	GetUnarchivedDevicesUseCase getUnarchivedDevices(GetUnarchivedDevicesPort getUnarchivedDevicesPort){
		return new GetUnarchivedDevicesService(getUnarchivedDevicesPort);
	}
}
