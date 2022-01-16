package it.deltax.produlytics.uibackend.adapters;

import it.deltax.produlytics.persistence.rilevazioni.Rilevazione;
import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.out.ListDetectionsByCharacteristicPort;
import it.deltax.produlytics.uibackend.db.rilevazioni.repositories.RilevazioneRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DetectionsPersistenceAdapterByCharacteristic implements ListDetectionsByCharacteristicPort {

    private final RilevazioneRepository repo;

    public DetectionsPersistenceAdapterByCharacteristic(RilevazioneRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<DetectionLight> listByCharacteristic(long machineId, String characteristicCode, Optional<Long> createdAfter) {
        List<Rilevazione> detections;
        if (createdAfter.isPresent()) {
            detections = repo.findByMacchinaAndCaratteristicaAndCreazioneUtcGreaterThan(machineId, characteristicCode, createdAfter.get());
        } else {
            detections = repo.findByMacchinaAndCaratteristica(machineId, characteristicCode);
        }
        return detections.stream()
                .map(rilevazione -> new DetectionLight(rilevazione.getValore(), rilevazione.getCreazioneUtc(), rilevazione.getAnomalo()))
                .collect(Collectors.toList());
    }
}
