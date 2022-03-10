package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RilevazioneRepository extends CrudRepository<DetectionEntity, DetectionEntityId> {

    // autoimplementazione da parte di spring
    // *Id*DeviceId perché fa parte della chiave composta (DetectionEntityId)
    List<DetectionEntity> findByIdDeviceIdAndIdCharacteristicId(int deviceId, int characteristicId, Sort sort);

    List<DetectionEntity> findByIdDeviceIdAndIdCharacteristicIdAndIdCreationTimeGreaterThan(
        int deviceId,
        int characteristicId,
        Instant creation,
        Sort sort
    );
}
