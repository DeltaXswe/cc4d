package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.repositories.UnarchivedCharacteristicRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UnarchivedCharacteristicAdapter
    implements FindAllUnarchivedCharacteristicsPort, FindCharacteristicLimitsPort {
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
    public Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId) {
        return repo.findById(new CharacteristicEntityId(deviceId, characteristicId))
            .map(characteristic -> new CharacteristicLimits(
                characteristic.getLowerLimit(),
                characteristic.getUpperLimit(),
                Optional.empty()
                )
            );
    }
}
