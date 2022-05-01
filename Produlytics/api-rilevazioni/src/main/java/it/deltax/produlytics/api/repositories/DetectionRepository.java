package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Questa interfaccia espone le query che è possibile effettuare sul database, in particolare sulla
 * tabella {@code detection}, lasciando a Spring il compito d'implementarla.
 */
@Repository
@SuppressWarnings("unused")
public interface DetectionRepository extends CrudRepository<DetectionEntity, DetectionEntityId> {
  /**
   * Questo metodo si occupa di ottenere le ultime {@code count} rilevazioni della caratteristica
   * specificata.
   *
   * @param deviceId l'identificativo della macchina
   * @param deviceId l'identificativo della macchina
   * @param deviceId l'identificativo della macchina
   * @return una lista di massimo {@code count} elementi con le ultime rilevazioni della caratteristica
   *     specificata
   */
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
      @Param("deviceId") int deviceId,
      @Param("characteristicId") int characteristicId,
      @Param("count") int count);

  /**
   * Questo metodo si occupa di marcare come anomala la rilevazione identificata dai tre parametri.
   *
   * @param deviceId l'identificativo della macchina a cui appartiene la rilevazione
   * @param characteristicId l'identificativo della caratteristica all'interno della macchina a cui
   *     appartiene la rilevazione
   * @param creationTime l'istante in cui è stata creata la rilevazione
   */
  @Transactional
  @Modifying
  @Query(value = """
    UPDATE DetectionEntity
    SET outlier = true
    WHERE deviceId = :deviceId
      AND characteristicId = :characteristicId
      AND creationTime = :creationTime
    """)
  void markOutlier(
      @Param("deviceId") int deviceId,
      @Param("characteristicId") int characteristicId,
      @Param("creationTime") Instant creationTime);
}
