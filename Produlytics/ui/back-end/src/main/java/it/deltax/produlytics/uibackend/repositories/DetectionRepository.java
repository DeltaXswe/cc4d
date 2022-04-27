package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Rappresenta il repository delle rilevazioni
 */
@Repository
public interface DetectionRepository extends CrudRepository<DetectionEntity, DetectionEntityId> {
	@Query(value = """
		select d
		from DetectionEntity d
		where d.deviceId = :deviceId
		and d.characteristicId = :characteristicId
		and d.creationTime < :olderThan
		order by creationTime desc
		""")
	List<DetectionEntity> findByCharacteristicAndCreationTimeGreaterThanQuery(
		@Param("deviceId") int deviceId,
		@Param("characteristicId") int characteristicId,
		@Param("olderThan") Instant olderThan
	);
}
