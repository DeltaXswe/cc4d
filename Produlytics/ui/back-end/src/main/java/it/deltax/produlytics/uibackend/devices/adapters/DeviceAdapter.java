package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindTinyDevicePort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DeviceAdapter implements FindAllUnarchivedDevicesPort, FindTinyDevicePort {

    private final DeviceRepository repo;

    public DeviceAdapter(DeviceRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TinyDevice> findAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
            .map(macchina ->
                new TinyDevice(macchina.getId(), macchina.getName())
            )
            .collect(Collectors.toList());
    }

    @Override
    public Optional<TinyDevice> find(int deviceId) {
        return repo.findById(deviceId)
            .map(macchina ->
                new TinyDevice(macchina.getId(), macchina.getName())
            );
    }

}
