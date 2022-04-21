package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Questa interfaccia espone le query che è possibile effettuare sul database, in particolare sulla tabella
 * `characteristic`, lasciando a Spring il compito d'implementarla.
 */
@Repository
@SuppressWarnings("unused")
public interface CharacteristicRepository extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {
	/**
	 * Questo metodo si occupa di cercare una caratteristica dato l'identificativo della sua macchina e il suo nome.
	 *
	 * @param deviceId L'identificativo della macchina, che deve esistere, a cui appartiene la caratteristica da cercare.
	 * @param name Il nome della caratteristica da cercare.
	 * @return Ritorna un entità rappresentante la caratteristica, se esiste, altrimenti ritorna `Optional.empty()`.
	 */
	Optional<CharacteristicEntity> findByDeviceIdAndName(int deviceId, String name);

	/**
	 * Questo metodo si occupa di ottenere i limiti tecnici e di processo della caratteristica specificata dai suoi argomenti.
	 *
	 * @param deviceId L'identificativo della macchina a cui appartiene la caratteristica;
	 * @param characteristicId L'identificativo della caratteristica all'interno della macchina.
	 * @return I limiti tecnici e di processo della caratteristica cercata.
	 */
	// COALESCE(STDDEV_SAMP(helper.value), 1) è necessario perchè STDDEV_SAMP ritorna `null` se viene
	// passato un solo valore. Il valore 1 è arbitrario, basta che sia != 0.
	@Query(value = """
		SELECT
			ch.auto_adjust as autoAdjust,
			ch.lower_limit as technicalLowerLimit,
			ch.upper_limit as technicalUpperLimit,
			mean_stddev.mean as computedMean,
			mean_stddev.stddev as computedStddev
		FROM characteristic ch JOIN (
			SELECT AVG(helper.value) as mean, COALESCE(STDDEV_SAMP(helper.value), 1) as stddev
			FROM (
				SELECT dt.value as value
				FROM detection dt
				WHERE dt.device_id = :deviceId AND dt.characteristic_id = :characteristicId
				ORDER BY dt.creation_time DESC
				LIMIT (
					SELECT COALESCE(sample_size, 0)
					FROM characteristic ch2
					WHERE ch2.device_id = :deviceId AND ch2.id = :characteristicId
				)
			) helper
		) mean_stddev
		ON ch.device_id = :deviceId AND ch.id = :characteristicId
		""", nativeQuery = true)
	LimitsEntity findLimits(@Param("deviceId") int deviceId, @Param("characteristicId") int characteristicId);
}
