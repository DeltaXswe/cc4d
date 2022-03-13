package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RilevazioneRepository extends CrudRepository<DetectionEntity, DetectionEntityId> {

    @Query("""
        select d from DetectionEntity d
        where d.id.deviceId = :deviceId
            and d.id.characteristicId = :characteristicId
            and (creation is null or d.id.creationTime > :creation)""")
    List<DetectionEntity> findByIdDeviceIdAndIdCharacteristicIdAndIdCreationTimeGreaterThan(
        @Param("deviceId") int deviceId,
        @Param("characteristicId") int characteristicId,
        @Param("creation") Instant creation,
        Sort sort
    );
}
