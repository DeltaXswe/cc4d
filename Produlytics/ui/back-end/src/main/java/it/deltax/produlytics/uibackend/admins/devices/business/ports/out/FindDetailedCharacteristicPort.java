package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;
import java.util.List;
import java.util.Optional;

/** La porta per l'ottenimento di una caratteristica di una macchina, dato gli identificativi */
public interface FindDetailedCharacteristicPort {
  Optional<DetailedCharacteristic> findByCharacteristic(int deviceId, int characteristicId);

  List<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name);
}
