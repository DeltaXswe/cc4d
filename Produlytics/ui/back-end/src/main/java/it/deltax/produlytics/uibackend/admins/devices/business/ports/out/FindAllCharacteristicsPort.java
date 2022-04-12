package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;

import java.util.List;

public interface FindAllCharacteristicsPort {
	public List<Characteristic> findAllByDeviceId(int deviceId);
}
