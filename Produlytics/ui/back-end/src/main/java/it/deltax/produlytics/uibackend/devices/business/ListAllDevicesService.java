package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDeviceInfo;
import it.deltax.produlytics.uibackend.devices.business.ports.in.ListAllDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.ListAllDevicesPort;

import java.util.List;

public class ListAllDevicesService implements ListAllDevicesUseCase {

    private final ListAllDevicesPort port;

    public ListAllDevicesService(ListAllDevicesPort port) {
        this.port = port;
    }

    @Override
    public List<UnarchivedDeviceInfo> listAll() {
        return port.listAll();
    }
}
