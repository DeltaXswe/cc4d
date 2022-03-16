package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@SuppressWarnings("unused")
public interface DetectionRepository extends CrudRepository<DetectionEntity, DetectionEntityId> {
	@Query(value = """
			SELECT *
			FROM (
				SELECT *
				FROM detection
				WHERE device_id = :deviceId AND characteristic_id = :characteristicId
				ORDER BY creation_time DESC
				LIMIT :count
			)
			ORDER BY creation_time ASC
		""", nativeQuery = true)
	List<DetectionEntity> findLastNById(
		@Param("deviceId") int deviceId, @Param("characteristicId") int characteristicId, @Param("count") int count
	);

	@Modifying
	@Query(value = """
			UPDATE detection
			SET outlier = true
			WHERE device_id = :deviceId AND characteristic_id = :characteristicId AND creation_time = :creationTime
		""", nativeQuery = true)
	void markOutlier(
		@Param("deviceId") int deviceId,
		@Param("characteristicId") int characteristicId,
		@Param("creationTime") Instant creationTime
	);
}
