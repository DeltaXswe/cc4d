package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Questa interfaccia espone le query che è possibile effettuare sul database, in particolare sulla
 * tabella {@code characteristic}, lasciando a Spring il compito d'implementarla.
 */
@Repository
@SuppressWarnings("unused")
public interface CharacteristicRepository
    extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {
  /**
   * Questo metodo si occupa di cercare una caratteristica dato l'identificativo della sua macchina
   * e il suo nome.
   *
   * @param deviceId l'identificativo della macchina, che deve esistere, a cui appartiene la
   *     caratteristica da cercare
   * @param name il nome della caratteristica da cercare
   * @return ritorna un entità rappresentante la caratteristica, se esiste, altrimenti ritorna
   *     {@code Optional.empty()}
   */
  Optional<CharacteristicEntity> findByDeviceIdAndName(int deviceId, String name);

  /**
   * Questo metodo si occupa di ottenere i limiti tecnici e di processo della caratteristica
   * specificata dai suoi argomenti.
   *
   * @param deviceId l'identificativo della macchina a cui appartiene la caratteristica
   * @param characteristicId l'identificativo della caratteristica all'interno della macchina
   * @return i limiti tecnici e di processo della caratteristica cercata
   */
  // COALESCE(STDDEV_SAMP(helper.value), 1) è necessario perchè STDDEV_SAMP ritorna {@code null} se viene
  // passato un solo valore. Il valore 1 è arbitrario, basta che sia != 0.
  @Query(value = """
    SELECT
      ch.auto_adjust as autoAdjust,
      ch.lower_limit as technicalLowerLimit,
      ch.upper_limit as technicalUpperLimit,
      mean_stddev.mean as computedMean,
      mean_stddev.stddev as computedStddev
    FROM characteristic ch JOIN (
      SELECT COALESCE(AVG(helper.value), 0) as mean, COALESCE(STDDEV_SAMP(helper.value), 1) as stddev
      FROM (
        SELECT dt.value as value
        FROM detection dt
        WHERE dt.device_id = :deviceId AND dt.characteristic_id = :characteristicId
        ORDER BY dt.creation_time DESC
        LIMIT (
          SELECT COALESCE(sample_size, (
            SELECT COUNT(*)
            FROM detection dt2
            WHERE dt2.device_id = :deviceId and dt2.characteristic_id = :characteristicId
          ))
          FROM characteristic ch2
          WHERE ch2.device_id = :deviceId AND ch2.id = :characteristicId
        )
      ) helper
    ) mean_stddev
    ON ch.device_id = :deviceId AND ch.id = :characteristicId
    """, nativeQuery = true)
  LimitsEntity findLimits(
      @Param("deviceId") int deviceId, @Param("characteristicId") int characteristicId);
}
