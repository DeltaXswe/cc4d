package it.deltax.produlytics.uibackend.devices.business.ports.out;


import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;

import java.util.List;

public interface GetUnarchivedDevicesPort {
    List<TinyDevice> getUnarchivedDevices();
}
