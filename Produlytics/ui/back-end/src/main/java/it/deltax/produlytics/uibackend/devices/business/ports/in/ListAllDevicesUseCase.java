package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDeviceInfo;

import java.util.List;

public interface ListAllDevicesUseCase {
    List<UnarchivedDeviceInfo> listAll();
}
