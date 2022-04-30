package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import java.util.Optional;

/**
 * La porta per l'ottenimento di una macchina con le informazioni essenziali, dato il suo
 * identificativo
 */
public interface FindTinyDevicePort {
  Optional<TinyDevice> findTinyDevice(int deviceId);
}
