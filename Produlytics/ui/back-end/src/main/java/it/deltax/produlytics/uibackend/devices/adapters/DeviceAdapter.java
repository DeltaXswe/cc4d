package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DeviceAdapter implements GetUnarchivedDevicesPort {

    private final DeviceRepository repo;

    public DeviceAdapter(DeviceRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TinyDevice> getUnarchivedDevices() {
        return StreamSupport.stream(repo.findByArchived(false).spliterator(), false)
            .map(device ->
                new TinyDevice(
                    device.id(),
                    device.name())
            )
            .collect(Collectors.toList());
    }
}
