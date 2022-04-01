package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetAllUnarchivedDevicesPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUnarchivedDevicesService implements GetUnarchivedDevicesUseCase {

    private final GetAllUnarchivedDevicesPort port;

    public GetUnarchivedDevicesService(GetAllUnarchivedDevicesPort port) {
        this.port = port;
    }

    @Override
    public List<TinyDevice> getUnarchivedDevices() {
        return port.getUnarchivedDevices();
    }
}
