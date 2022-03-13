package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDeviceInfo;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindDevicePort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.ListAllDevicesPort;
import it.deltax.produlytics.uibackend.repositories.MacchinaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DeviceLightPersistenceAdapter implements ListAllDevicesPort, FindDevicePort {

    private final MacchinaRepository repo;

    public DeviceLightPersistenceAdapter(MacchinaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<UnarchivedDeviceInfo> listAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
            .map(macchina ->
                new UnarchivedDeviceInfo(macchina.getId(), macchina.getName())
            )
            .collect(Collectors.toList());
    }

    @Override
    public Optional<UnarchivedDeviceInfo> find(int machineId) {
        return repo.findById(machineId)
            .map(macchina ->
                new UnarchivedDeviceInfo(macchina.getId(), macchina.getName())
            );
    }
}
