package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedDevicesPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUnarchivedDevicesService implements GetUnarchivedDevicesUseCase {

    private final FindAllUnarchivedDevicesPort port;

    public GetUnarchivedDevicesService(FindAllUnarchivedDevicesPort port) {
        this.port = port;
    }

    @Override
    public List<UnarchivedDevice> getAll() {
        return port.findAll();
    }
}
