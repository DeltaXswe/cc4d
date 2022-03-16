package it.deltax.produlytics.uibackend.detections.adapters;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.detections.business.ports.out.ListDetectionsByCharacteristicPort;
import it.deltax.produlytics.uibackend.repositories.DetectionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DetectionsPersistenceAdapter implements ListDetectionsByCharacteristicPort {

    private final DetectionRepository repo;

    public DetectionsPersistenceAdapter(DetectionRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<DetectionLight> listByCharacteristic(int deviceId, int characteristicId, Long createdAfter) {
        List<DetectionEntity> detections;
        detections = repo.findByIdDeviceIdAndIdCharacteristicIdAndIdCreationTimeGreaterThan(
            deviceId,
            characteristicId,
            Instant.ofEpochMilli(createdAfter),
            Sort.by("creazioneUtc")
        );
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
