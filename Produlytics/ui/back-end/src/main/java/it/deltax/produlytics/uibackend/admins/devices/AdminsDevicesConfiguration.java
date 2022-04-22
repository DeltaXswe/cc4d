package it.deltax.produlytics.uibackend.admins.devices;

import it.deltax.produlytics.uibackend.admins.devices.business.CreateDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.*;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.*;
import it.deltax.produlytics.uibackend.admins.devices.business.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe per la configurazione di Spring che descrive come creare le classi di business.
 */
@Configuration
public class AdminsDevicesConfiguration {
	/**
	 * Crea un'istanza di GetCharacteristicsUseCase
	 * @param findAllCharacteristicsPort la porta per ottenere le caratteristiche, da passare al costruttore di
	 *                                   GetCharacteristicsService
	 * @return la nuova istanza di GetCharacteristicsService
	 */
	@Bean
	GetCharacteristicsUseCase getCharacteristicsUseCase(FindAllCharacteristicsPort findAllCharacteristicsPort){
		return new GetCharacteristicsService(findAllCharacteristicsPort);
	}

	/**
	 * Crea un'istanza di GetDeviceDetailsUseCase
	 * @param findDetailedDevicePort la porta per ottenere una macchina dettagliata, da passare al costruttore di
	 *                               GetDeviceDetailsService
	 * @return la nuova istanza di GetDeviceDetailsService
	 */
	@Bean
	GetDeviceDetailsUseCase getDeviceDetailsUseCase(FindDetailedDevicePort findDetailedDevicePort){
		return new GetDeviceDetailsService(findDetailedDevicePort);
	}

	/**
	 * Crea un'istanza di GetDevicesUseCase
	 * @param getDevicesPort la porta per ottenere le macchine, da passare al costruttore di GetDevicesService
	 * @return la nuova istanza di GetDevicesService
	 */
	@Bean
	GetDevicesUseCase getDevicesUseCase(GetDevicesPort getDevicesPort){
		return new GetDevicesService(getDevicesPort);
	}

	/**
	 * Crea un'istanza di InsertCharacteristicUseCase
	 * @param insertCharacteristicPort la porta per l'inserimento di una caratteristica, da passare al costruttore di
	 *                                 InsertCharacteristicService
	 * @param findDevicePort la porta per cercare una macchina, da passare al costruttore di
	 *                       InsertCharacteristicService
	 * @param findCharacteristicPort la porta per cercare una caratteristica, da passare ala costruttore di
	 *                               InsertCharacteristicService
	 * @return la nuova istanza di InsertCharacteristicService
	 */
	@Bean
	InsertCharacteristicUseCase insertCharacteristicUseCase(InsertCharacteristicPort insertCharacteristicPort,
		FindDetailedDevicePort findDevicePort,
		FindDetailedCharacteristicPort findCharacteristicPort){
		return new InsertCharacteristicService(insertCharacteristicPort, findDevicePort, findCharacteristicPort);
	}

	/**
	 * Crea un'istanza di InsertDeviceUseCase
	 * @param findTinyDeviceByNamePort la porta per cercare una macchina con le informazioni essenziali, da passare
	 *                                 al costruttore di InsertDeviceService
	 * @param insertDevicePort la porta per inserire una macchina, da passare aal costruttore di InsertDeviceService
	 * @param createDevice l'istanza di una classe per creare una nuova macchina, da passare al costruttore di
	 *                     InsertDeviceService
	 * @param insertCharacteristicUseCase l'interfaccia che modella il caso d'uso di inserimento di una caratteristica,
	 *                                    da passare al costruttore di InsertDeviceService
	 * @return la nuova istanza di InsertDeviceService
	 */
	@Bean
	InsertDeviceUseCase insertDeviceUseCase(FindTinyDeviceByNamePort findTinyDeviceByNamePort,
		InsertDevicePort insertDevicePort,
		CreateDevice createDevice,
		InsertCharacteristicUseCase insertCharacteristicUseCase){
		return new InsertDeviceService(
			findTinyDeviceByNamePort, insertDevicePort, createDevice, insertCharacteristicUseCase);
	}

	/**
	 * Crea un'istanza di UpdateCharacteristicArchiveStatusUseCase
	 * @param findCharacteristicPort la porta per cercare una caratteristica, da passare al costruttore di
	 *                               UpdateCharacteristicArchiveStatusService
	 * @param updateCharacteristicPort la porta per aggiornare una caratteristica, da passare al costruttore di
	 *                                 UpdateCharacteristicArchiveStatusService
	 * @return la nuova istanza di UpdateCharacteristicArchiveStatusService
	 */
	@Bean
	UpdateCharacteristicArchiveStatusUseCase updateCharacteristicArchiveStatusUseCase(
		FindDetailedCharacteristicPort findCharacteristicPort,
		UpdateCharacteristicPort updateCharacteristicPort){
		return new UpdateCharacteristicArchiveStatusService(findCharacteristicPort, updateCharacteristicPort);
	}

	/**
	 * Crea un'istanza di UpdateCharacteristicUseCase
	 * @param findCharacteristicPort la porta per cercare una caratteristica, da passare al costruttore di
	 *                               UpdateCharacteristicService
	 * @param updateCharacteristicPort la porta per aggiornare una caratteristica, da passare al costruttore di
	 *                                 UpdateCharacteristicService
	 * @return la nuova istanza di UpdateCharacteristicService
	 */
	@Bean
	UpdateCharacteristicUseCase updateCharacteristicUseCase(
		FindDetailedCharacteristicPort findCharacteristicPort,
		UpdateCharacteristicPort updateCharacteristicPort){
		return new UpdateCharacteristicService(findCharacteristicPort, updateCharacteristicPort);
	}

	/**
	 * Crea un'istanza di UpdateDeviceArchiveStatusUseCase
	 * @param findDetailedDevicePort la porta per cercare una macchina dettagliata, da passare al costruttore di
	 *                               UpdateDeviceArchiveStatusService
	 * @param updateDeviceArchiveStatus la porta per aggiornare lo stato di archiviazione di una macchina, da passare
	 *                                  al costruttore di UpdateDeviceArchiveStatusService
	 * @return la nuova istanza di UpdateDeviceArchiveStatusService
	 */
	@Bean
	UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase(
		FindDetailedDevicePort findDetailedDevicePort,
	 	UpdateDeviceArchiveStatusPort updateDeviceArchiveStatus){
		return new UpdateDeviceArchiveStatusService(findDetailedDevicePort, updateDeviceArchiveStatus);
	}

	/**
	 * Crea un'istanza di UpdateDeviceDeactivateStatusUseCase
	 * @param findDetailedDevicePort la porta per cercare una macchina dettagliata, da passare al costruttore di
	 *		                         UpdateDeviceDeactivateStatusService
	 * @param updateDeviceDeactivateStatusPort la porta per aggiornare lo stato di attivazione di una macchina, da
	 *                                         passare al costruttore di UpdateDeviceDeactivateStatusService
	 * @return la nuova istanza di UpdateDeviceDeactivateStatusService
	 */
	@Bean
	UpdateDeviceDeactivateStatusUseCase updateDeviceDeactivateStatusUseCase(
		FindDetailedDevicePort findDetailedDevicePort,
		UpdateDeviceDeactivateStatusPort updateDeviceDeactivateStatusPort){
		return new UpdateDeviceDeactivateStatusService(findDetailedDevicePort, updateDeviceDeactivateStatusPort);
	}

	/**
	 * Crea un'istanza di UpdateDeviceNameUseCase
	 * @param findDetailedDevicePort la porta per cercare una macchina dettagliata, da passare al costruttore di
	 * 			                     UpdateDeviceNameService
	 * @param findTinyDeviceByNamePort la porta per cercare una macchina con le informazioni essenziali, da passare
	 *                                 al costruttore di UpdateDeviceNameService
	 * @param updateDeviceNamePort la porta per aggiornare il nome di una macchina, da passare al costruttore di
	 *                             UpdateDeviceNameService
	 * @return la nuova istanza di UpdateDeviceNameService
	 */
	@Bean
	UpdateDeviceNameUseCase updateDeviceNameUseCase(
		FindDetailedDevicePort findDetailedDevicePort,
		FindTinyDeviceByNamePort findTinyDeviceByNamePort,
		UpdateDeviceNamePort updateDeviceNamePort){
		return new UpdateDeviceNameService(findDetailedDevicePort, findTinyDeviceByNamePort, updateDeviceNamePort);
	}

}
