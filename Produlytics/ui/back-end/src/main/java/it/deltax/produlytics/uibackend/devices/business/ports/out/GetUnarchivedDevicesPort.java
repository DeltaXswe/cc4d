package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import java.util.List;

/** La porta per l'ottenimento delle macchine non archiviate */
public interface GetUnarchivedDevicesPort {
  List<TinyDevice> getUnarchivedDevices();
}
