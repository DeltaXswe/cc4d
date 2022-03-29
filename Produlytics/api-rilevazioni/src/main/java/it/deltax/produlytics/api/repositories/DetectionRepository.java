package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

// Repository per interagire con la tabella `detection`.
@Repository
@SuppressWarnings("unused")
public interface DetectionRepository extends CrudRepository<DetectionEntity, DetectionEntityId> {
	// Trova le ultime `count` rilevazioni di una caratteristica, ordinate per data di creazione.
	@Query(value = """
			SELECT *
			FROM (
				SELECT *
				FROM detection
				WHERE device_id = :deviceId AND characteristic_id = :characteristicId
				ORDER BY creation_time DESC
				LIMIT :count
			) helper
			ORDER BY creation_time ASC
		""", nativeQuery = true)
	List<DetectionEntity> findLastDetectionsById(
		@Param("deviceId") int deviceId, @Param("characteristicId") int characteristicId, @Param("count") int count
	);

	// Marca una rilevazione come anomala.
	@Transactional
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
