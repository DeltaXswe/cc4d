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
	@Query("SELECT *"
		+ "FROM ("
		+ "    SELECT * detection"
		+ "    WHERE characteristic_id = :characteristicId AND device_id = :device_id"
		+ "    ORDER BY creation_time DESC"
		+ "    LIMIT :count"
		+ ")"
		+ "ORDER BY creation_time ASC")
	List<DetectionEntity> findLastNById(
		@Param("deviceId") int deviceId, @Param("characteristicId") int characteristicId, @Param("count") int count
	);

	@Modifying
	@Query("UPDATE detection"
		+ "SET outlier = true"
		+ "WHERE device_id = :device_id AND characteristic_id = := characteristicId AND creationTime = :creationTime")
	void markOutlier(
		@Param("deviceId") int deviceId,
		@Param("characteristicId") int characteristicId,
		@Param("creationTime") Instant creationTime
	);
}
