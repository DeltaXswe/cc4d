package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import java.util.List;

/** Interfaccia che rappresenta il caso d'uso di ottenimento delle macchine non archiviate. */
public interface GetUnarchivedDevicesUseCase {
  List<TinyDevice> getUnarchivedDevices();
}
