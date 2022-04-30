package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;
import java.util.List;

/** La porta per l'ottenimento della lista delle macchine */
public interface GetDevicesPort {
  List<Device> getDevices();
}
