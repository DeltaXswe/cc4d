package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// Repository per interagire con la tabella `characteristic`.
@Repository
@SuppressWarnings("unused")
public interface CharacteristicRepository extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {
	// Trova i limiti tecnici e di processo di una caratteristica.
	@Query(value = """
		SELECT
			ch.auto_adjust as autoAdjust,
			ch.lower_limit as technicalLowerLimit,
			ch.upper_limit as technicalUpperLimit,
			mean_stddev.mean as computedMean,
			mean_stddev.stddev as computedStddev
		FROM characteristic ch JOIN (
			SELECT AVG(value) as mean, STDDEV_SAMP(value) as stddev
			FROM (
				SELECT dt.value
				FROM detection dt
				WHERE dt.device_id = :deviceId AND dt.characteristic_id = :characteristicId
				ORDER BY dt.creation_time DESC
				LIMIT (
					SELECT sample_size
					FROM characteristic ch2
					WHERE ch2.device_id = :deviceId AND ch2.id = :characteristicId
				)
			) helper
		) mean_stddev
		ON ch.device_id = :deviceId AND ch.id = :characteristicId
		""", nativeQuery = true)
	LimitsEntity findLimits(@Param("deviceId") int deviceId, @Param("characteristicId") int characteristicId);
}
