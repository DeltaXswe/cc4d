package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.uibackend.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindTinyDevicePort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetAllUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetDevicesPort;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DeviceAdapter implements GetDevicesPort,
    GetAllUnarchivedDevicesPort,
    FindTinyDevicePort {

    private final DeviceRepository repo;

    public DeviceAdapter(DeviceRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Device> getDevices() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
            .map(macchina ->
                new Device(macchina.getId(), macchina.getName(), macchina.getArchived(), macchina.getDeactivated())
            )
            .collect(Collectors.toList());
    }

    @Override
    public List<TinyDevice> getUnarchivedDevices() { //TODO siamo sicuri ritorni solo le non archiviate?
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
