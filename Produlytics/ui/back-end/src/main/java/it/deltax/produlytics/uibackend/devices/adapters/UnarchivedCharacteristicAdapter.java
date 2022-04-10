package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UnarchivedCharacteristicAdapter
    implements FindAllUnarchivedCharacteristicsPort, FindCharacteristicLimitsPort {
    private final CharacteristicRepository repo;

    public UnarchivedCharacteristicAdapter(CharacteristicRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CharacteristicTitle> findAllByDeviceId(int deviceId) {
        return this.repo.findByArchivedFalseAndId_DeviceId(deviceId)
                .stream()
                .map(characteristicEntity -> new CharacteristicTitle(
                    characteristicEntity.getId().getId(),
                    characteristicEntity.getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId) {
        return this.repo.findById(new CharacteristicEntityId(deviceId, characteristicId))
            .map(characteristic -> CharacteristicLimits.newCharacteristicLimits(
                characteristic.getLowerLimit(),
                characteristic.getUpperLimit()
                )
            );
    }
}
