package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** Rappresenta il repository delle caratteristiche. */
@Repository
public interface CharacteristicRepository
    extends JpaRepository<CharacteristicEntity, CharacteristicEntityId> {
  List<CharacteristicEntity> findByArchivedFalseAndDeviceIdOrderByName(int deviceId);

  List<CharacteristicEntity> findAllByDeviceIdOrderByName(int deviceId);

  @Query(
      value = "SELECT * FROM characteristic WHERE device_id = :deviceId AND name = :name",
      nativeQuery = true)
  Optional<CharacteristicEntity> findByDeviceIdAndName(
      @Param("deviceId") int deviceId, @Param("name") String name);

  @Query(value = """
    SELECT
      ch.auto_adjust as autoAdjust,
      ch.lower_limit as technicalLowerLimit,
      ch.upper_limit as technicalUpperLimit,
      mean_stddev.mean as computedMean,
      mean_stddev.stddev as computedStddev
    FROM characteristic ch, device d, (
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
    WHERE ch.device_id = :deviceId
        AND ch.id = :characteristicId
        AND d.id = :deviceId
        AND NOT ch.archived
        AND NOT d.archived
    """, nativeQuery = true)
  Optional<LimitsEntity> findLimits(
      @Param("deviceId") int deviceId, @Param("characteristicId") int characteristicId);

  interface LimitsEntity {
    boolean getAutoAdjust();
    Optional<Double> getTechnicalLowerLimit();
    Optional<Double> getTechnicalUpperLimit();
    double getComputedMean();
    double getComputedStddev();
  }
}
