package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;

public interface UpdateCharacteristicPort {
	void updateCharacteristic(DetailedCharacteristic characteristic);
}
