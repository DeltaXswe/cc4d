package it.deltax.produlytics.uibackend.adapters;

import it.deltax.produlytics.persistence.rilevazioni.Rilevazione;
import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.out.ListDetectionsPort;
import it.deltax.produlytics.uibackend.db.rilevazioni.repositories.RilevazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DetectionsPersistenceAdapter implements ListDetectionsPort {

    private final RilevazioneRepository repo;

    public DetectionsPersistenceAdapter(RilevazioneRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<DetectionLight> listDetections(long machineId, String characteristicName, Optional<Long> createdAfter) {
        List<Rilevazione> detections;
        if (createdAfter.isPresent()) {
            detections = repo.findByMacchinaAndCaratteristicaAndCreazioneUtcGreaterThan(machineId, characteristicName, createdAfter.get());
        } else {
            detections = repo.findByMacchinaAndCaratteristica(machineId, characteristicName);
        }
        return detections.stream()
                .map(rilevazione -> new DetectionLight(rilevazione.getValore(), rilevazione.getCreazioneUtc(), rilevazione.getAnomalo()))
                .collect(Collectors.toList());
    }
}
