package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface CharacteristicRepository extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {
	@Query(value = """
		SELECT
			ch.lower_limit as technicalLowerLimit,
			ch.upper_limit as technicalUpperLimit,
			mean_stddev.mean as computedMean,
			mean_stddev.stddev as computedStddev
		FROM characteristic ch JOIN (
			SELECT AVG(dt.value) as mean, STDDEV_SAMP(dt.value) as stddev
			FROM detections dt
			WHERE dt.device_id = :deviceId AND dt.characteristic_id = :characteristicId
			ORDER BY dt.creation_time DESC
			LIMIT (
				SELECT sample_size
				FROM characteristic ch2
				WHERE ch2.device_id = :deviceId AND ch2.id = :charactersiticId
			)
		) mean_stddev
		ON ch.device_id = dt.device_id AND ch.id = dt.characteristic_id
		""", nativeQuery = true)
	LimitsEntity findLimits(@Param("deviceId") int deviceId, @Param("characteristicId") int characteristicId);
}
