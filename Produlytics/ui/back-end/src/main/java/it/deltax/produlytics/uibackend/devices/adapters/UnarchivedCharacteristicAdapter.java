package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyCharacteristic;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * L'adapter dello strato di persistenza per le operazioni riguardanti le caratteristiche
 */
@Component
public class UnarchivedCharacteristicAdapter implements FindAllUnarchivedCharacteristicsPort,
    FindCharacteristicLimitsPort {
    private final CharacteristicRepository repo;

    /**
     * Il costruttore
     * @param repo lo strato di persistenza con i dati sulle caratteristiche
     */
    public UnarchivedCharacteristicAdapter(CharacteristicRepository repo) {
        this.repo = repo;
    }

    /**
     * Restituisce la lista di tutte le caratteristiche non archiviate di una macchina
     * @param deviceId l'id della macchina
     * @return la lista di tutte le caratteristiche non archiviate
     */
    @Override
    public List<TinyCharacteristic> findAllByDeviceId(int deviceId) {
        return this.repo.findByArchivedFalseAndDeviceId(deviceId)
                .stream()
                .map(characteristicEntity -> new TinyCharacteristic(
                    characteristicEntity.getId(),
                    characteristicEntity.getName()
                ))
                .toList();
    }

    /**
     * Restituisce i limiti tecnci della caratteristica non archiviata di una macchina
     * @param deviceId l'id della macchina
     * @param characteristicId l'id della caratteristica
     * @return i limiti della caratteristica, se esiste
     */
    @Override
    public Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId) {
        return this.repo.findByArchivedFalseAndDeviceIdAndId(deviceId, characteristicId)
            .map(characteristic -> CharacteristicLimits.newCharacteristicLimits(
                characteristic.getLowerLimit(),
                characteristic.getUpperLimit()
                )
            );
    }
}
