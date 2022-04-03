package it.deltax.produlytics.uibackend.devices.business.domain.out;


import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;

import java.util.List;

// per operazioni in cui serve solo id e nome
public interface GetAllUnarchivedDevicesPort {
    List<TinyDevice> getUnarchivedDevices();
}
