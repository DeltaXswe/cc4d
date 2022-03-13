package it.deltax.produlytics.uibackend.detections.adapters;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.detections.business.ports.out.ListDetectionsByCharacteristicPort;
import it.deltax.produlytics.uibackend.repositories.RilevazioneRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DetectionsPersistenceAdapter implements ListDetectionsByCharacteristicPort {

    private final RilevazioneRepository repo;

    public DetectionsPersistenceAdapter(RilevazioneRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<DetectionLight> listByCharacteristic(int machineId, int characteristicId, Optional<Long> createdAfter) {
        List<DetectionEntity> detections;
        if (createdAfter.isPresent()) {
            detections = repo.findByIdDeviceIdAndIdCharacteristicIdAndIdCreationTimeGreaterThan(
                machineId,
                characteristicId,
                Instant.ofEpochMilli(createdAfter.get()),
                Sort.by("creazioneUtc")
            );
        } else {
            detections = repo.findByIdDeviceIdAndIdCharacteristicId(
                machineId,
                characteristicId,
                Sort.by("creazioneUtc")
            );
        }
        return detections.stream()
            .map(rilevazione ->
                new DetectionLight(
                    rilevazione.getValue(),
                    rilevazione.getId().getCreationTime().getEpochSecond(),
                    rilevazione.getOutlier()
                )
            )
            .collect(Collectors.toList());
    }
}