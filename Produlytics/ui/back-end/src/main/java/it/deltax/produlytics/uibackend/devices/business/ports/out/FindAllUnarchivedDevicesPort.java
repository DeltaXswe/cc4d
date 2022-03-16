package it.deltax.produlytics.uibackend.devices.business.ports.out;


import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDevice;

import java.util.List;

public interface FindAllUnarchivedDevicesPort {
    List<UnarchivedDevice> findAll();
}
