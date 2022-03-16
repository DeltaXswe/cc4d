package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.repositories.UnarchivedCharacteristicRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UnarchivedCharacteristicAdapter implements FindAllUnarchivedCharacteristicPort, FindCharacteristicPort {

    private final UnarchivedCharacteristicRepository repo;

    public UnarchivedCharacteristicAdapter(UnarchivedCharacteristicRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CharacteristicTitle> findAllByDeviceId(int deviceId) {
        return repo.findByArchivedFalseAndId_DeviceId(deviceId)
                .stream()
                .map(characteristicEntity -> new CharacteristicTitle(
                    characteristicEntity.getId().getId(),
                    characteristicEntity.getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Characteristic> find(int deviceId, int id) {
        return repo.findById(new CharacteristicEntityId(deviceId, id))
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
