package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import java.util.Optional;

/** La porta per l'ottenimento di una macchina dettagliata, dato il suo identificativo */
public interface FindDetailedDevicePort {
  Optional<DetailedDevice> findDetailedDevice(int deviceId);
}
