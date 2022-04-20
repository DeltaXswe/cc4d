package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;

import java.util.Optional;

public interface FindTinyDeviceByNamePort {
	Optional<TinyDevice> findByName(String name);

}
