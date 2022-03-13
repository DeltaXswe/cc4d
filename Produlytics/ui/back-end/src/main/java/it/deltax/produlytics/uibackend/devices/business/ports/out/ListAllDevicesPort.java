package it.deltax.produlytics.uibackend.devices.business.ports.out;


import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDeviceInfo;

import java.util.List;

public interface ListAllDevicesPort {
    List<UnarchivedDeviceInfo> listAll();
}
