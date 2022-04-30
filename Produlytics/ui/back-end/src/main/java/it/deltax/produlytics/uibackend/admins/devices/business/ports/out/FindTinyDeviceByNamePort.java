package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import java.util.Optional;

/** La porta per l'ottenimento di una macchina con le informazioni essenziali, dato il suo nome */
public interface FindTinyDeviceByNamePort {
  Optional<TinyDevice> findByName(String name);
}
