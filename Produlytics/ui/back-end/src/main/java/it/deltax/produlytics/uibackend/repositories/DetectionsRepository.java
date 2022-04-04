package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DetectionsRepository extends CrudRepository<DetectionEntity, DetectionEntityId> {

	@Query(value = """
		select d from detection d
		where d.device_id = :device_id
		    and d.characteristic_id = :characteristic_id
		    and (creation_time is null or d.creation_time > :creation_time)
		order by creation_time """, nativeQuery = true)
	List<DetectionEntity> findByIdDeviceIdAndIdCharacteristicIdAndIdCreationTimeGreaterThan(
		@Param("device_id") int device_id,
		@Param("characteristic_id") int characteristic_id,
		@Param("creation_time") Instant creation_time
	);

}
