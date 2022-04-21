package it.deltax.produlytics.uibackend.admins.devices;

import it.deltax.produlytics.uibackend.admins.devices.business.CreateDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.*;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.*;
import it.deltax.produlytics.uibackend.admins.devices.business.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminsDevicesConfiguration {
	@Bean
	GetCharacteristicsUseCase getCharacteristicsUseCase(FindAllCharacteristicsPort findAllCharacteristicsPort){
		return new GetCharacteristicsService(findAllCharacteristicsPort);
	}

	@Bean
	GetDeviceDetailsUseCase getDeviceDetailsUseCase(FindDetailedDevicePort findDetailedDevicePort){
		return new GetDeviceDetailsService(findDetailedDevicePort);
	}

	@Bean
	GetDevicesUseCase getDevicesUseCase(GetDevicesPort getDevicesPort){
		return new GetDevicesService(getDevicesPort);
	}

	@Bean
	InsertCharacteristicUseCase insertCharacteristicUseCase(InsertCharacteristicPort insertCharacteristicPort,
		FindDetailedDevicePort findDevicePort,
		FindDetailedCharacteristicPort findCharacteristicPort){
		return new InsertCharacteristicService(insertCharacteristicPort, findDevicePort, findCharacteristicPort);
	}

	@Bean
	InsertDeviceUseCase insertDeviceUseCase(FindTinyDeviceByNamePort findTinyDeviceByNamePort,
		InsertDevicePort insertDevicePort,
		CreateDevice createDevice,
		InsertCharacteristicUseCase insertCharacteristicUseCase){
		return new InsertDeviceService(
			findTinyDeviceByNamePort, insertDevicePort, createDevice, insertCharacteristicUseCase);
	}

	@Bean
	UpdateCharacteristicArchiveStatusUseCase updateCharacteristicArchiveStatusUseCase(
		FindDetailedCharacteristicPort findCharacteristicPort,
		UpdateCharacteristicPort updateCharacteristicPort){
		return new UpdateCharacteristicArchiveStatusService(findCharacteristicPort, updateCharacteristicPort);
	}

	@Bean
	UpdateCharacteristicUseCase updateCharacteristicUseCase(
		FindDetailedCharacteristicPort findCharacteristicPort,
		UpdateCharacteristicPort updateCharacteristicPort){
		return new UpdateCharacteristicService(findCharacteristicPort, updateCharacteristicPort);
	}

	@Bean
	UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase(
		FindDetailedDevicePort findDetailedDevicePort,
	 	UpdateDeviceArchiveStatusPort updateDeviceArchiveStatus){
		return new UpdateDeviceArchiveStatusService(findDetailedDevicePort, updateDeviceArchiveStatus);
	}

	@Bean
	UpdateDeviceDeactivateStatusUseCase updateDeviceDeactivateStatusUseCase(
		FindDetailedDevicePort findDetailedDevicePort,
		UpdateDeviceDeactivateStatusPort updateDeviceDeactivateStatusPort){
		return new UpdateDeviceDeactivateStatusService(findDetailedDevicePort, updateDeviceDeactivateStatusPort);
	}

	@Bean
	UpdateDeviceNameUseCase updateDeviceNameUseCase(
		FindDetailedDevicePort findDetailedDevicePort,
		FindTinyDeviceByNamePort findTinyDeviceByNamePort,
		UpdateDeviceNamePort updateDeviceNamePort){
		return new UpdateDeviceNameService(findDetailedDevicePort, findTinyDeviceByNamePort, updateDeviceNamePort);
	}

}
