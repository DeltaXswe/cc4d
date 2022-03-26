package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicPort;
import it.deltax.produlytics.uibackend.repositories.UnarchivedCharacteristicRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// object adapter (Target sono le port, Adaptee è la repo)

@Component
public class UnarchivedCharacteristicAdapter implements FindAllUnarchivedCharacteristicPort, FindCharacteristicPort {

    private final UnarchivedCharacteristicRepository repo;

    public UnarchivedCharacteristicAdapter(UnarchivedCharacteristicRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<List<CharacteristicTitle>> findAllByDeviceId(int deviceId) {
        return Optional.of(repo.findByArchivedFalseAndDeviceId(deviceId)
            .orElse(Collections.emptyList())
            .stream()
            .map(
                characteristic -> new CharacteristicTitle(
                    characteristic.getId().getId(),
                    characteristic.getName()
                )
            )
            .collect(Collectors.toList()));
    }

    @Override
    public Optional<Characteristic> find(int machineId, int id) {
        return repo.findById(new CharacteristicEntityId(machineId, id))
            .map(caratteristica ->
                new Characteristic(
                    caratteristica.getId().getId(),
                    caratteristica.getName(),
                    caratteristica.getId().getDeviceId(),
                    Optional.ofNullable(caratteristica.getLowerLimit()),
                    Optional.ofNullable(caratteristica.getUpperLimit()),
                    Optional.ofNullable(caratteristica.getAverage())
                )
            );
    }
}
