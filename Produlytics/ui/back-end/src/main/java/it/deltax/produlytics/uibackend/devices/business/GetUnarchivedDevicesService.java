package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUnarchivedDevicesService implements GetUnarchivedDevicesUseCase {

    private final GetUnarchivedDevicesPort getUnarchivedDevicesPort;


    /**
     * Il costruttore
     * @param getUnarchivedDevicesPort la porta per ottenere le macchine non archiviate
     */
    public GetUnarchivedDevicesService(GetUnarchivedDevicesPort getUnarchivedDevicesPort) {
        this.getUnarchivedDevicesPort = getUnarchivedDevicesPort;
    }


    /**
     * Restituisce le macchine non archiviate
     * @return la lista delle macchine non archiviate, ognuna con id e nome
     */
    @Override
    public List<TinyDevice> getUnarchivedDevices() {
        return this.getUnarchivedDevicesPort.getUnarchivedDevices();
    }
}
