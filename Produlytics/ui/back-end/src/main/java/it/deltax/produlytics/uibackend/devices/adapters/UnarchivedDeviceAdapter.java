package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * L'adapter dello strato di persistenza per le operazioni riguardanti le macchine
 */
@Component
public class UnarchivedDeviceAdapter implements GetUnarchivedDevicesPort {
    private final DeviceRepository repo;


    /**
     * Il costruttore
     * @param repo lo strato di persistenza con i dati delle macchine
     */
    public UnarchivedDeviceAdapter(DeviceRepository repo) {
        this.repo = repo;
    }


    /**
     * Restituisce le macchine non archiviate
     * @return la lista delle macchine non archiviate
     */
    @Override
    public List<TinyDevice> getUnarchivedDevices() {
        return StreamSupport.stream(this.repo.findByArchived(false).spliterator(), false)
            .map(device ->
                new TinyDevice(
                    device.id(),
                    device.name())
            )
            .collect(Collectors.toList());
    }
}
