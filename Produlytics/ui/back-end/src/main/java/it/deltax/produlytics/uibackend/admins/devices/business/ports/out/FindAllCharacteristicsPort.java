package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;

import java.util.List;

/**
 * La porta per l'ottenimento di tutte le caratteristiche di una macchina
 */
public interface FindAllCharacteristicsPort {
	List<Characteristic> findAllByDeviceId(int deviceId);
}
