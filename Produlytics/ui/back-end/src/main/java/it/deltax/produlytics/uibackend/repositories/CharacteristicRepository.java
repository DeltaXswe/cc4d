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


  /**
   * Questo metodo si occupa di cercare una caratteristica dato il suo identificativo globale.
   *
   * @param deviceId l'identificativo della macchina, che deve esistere, a cui appartiene la
   *     caratteristica da cercare
   * @param characteristicId l'identificativo della caratteristica all'interno della macchina
   * @return ritorna un entità rappresentante la caratteristica, se esiste, altrimenti ritorna
   *     {@code Optional.empty()}
   */
  @Query(value = """
      SELECT *
      FROM characteristic
      WHERE device_id = :deviceId
        AND id = :characteristicId
        AND NOT archived
        AND NOT (SELECT archived FROM device where id = :deviceId)
      """,
      nativeQuery = true)
  Optional<CharacteristicEntity> findActiveByDeviceIdAndId(int deviceId, int characteristicId);

  /**
   * Questo metodo si occupa di ottenere media e deviazione standard delle ultime rilevazioni
   * della caratteristica specificata dai suoi argomenti.
   *
   * @param deviceId l'identificativo della macchina a cui appartiene la caratteristica
   * @param characteristicId l'identificativo della caratteristica all'interno della macchina
   * @param sampleSize il numero di rilevazioni da considerare
   * @return media e deviazione standard della caratteristica cercata
   */
  @Query(value = """
      SELECT COALESCE(AVG(value), 0) as mean, COALESCE(STDDEV_SAMP(value), 1) as stddev
      FROM detection
      WHERE device_id = :deviceId AND characteristic_id = :characteristicId
      ORDER BY creation_time DESC
      LIMIT :sampleSize
      """, nativeQuery = true)
  MeanStddevEntity meanStddevWithSampleSize(
      @Param("deviceId") int deviceId,
      @Param("characteristicId") int characteristicId,
      @Param("sampleSize") double sampleSize
  );

  /**
   * Questo metodo si occupa di ottenere media e deviazione standard delle rilevazioni
   * della caratteristica specificata dai suoi argomenti.
   *
   * @param deviceId l'identificativo della macchina a cui appartiene la caratteristica
   * @param characteristicId l'identificativo della caratteristica all'interno della macchina
   * @return media e deviazione standard della caratteristica cercata
   */
  @Query(value = """
      SELECT COALESCE(AVG(value), 0) as mean, COALESCE(STDDEV_SAMP(value), 1) as stddev
      FROM detection
      WHERE device_id = :deviceId AND characteristic_id = :characteristicId
      """, nativeQuery = true)
  MeanStddevEntity meanStddevWithoutSampleSize(
      @Param("deviceId") int deviceId,
      @Param("characteristicId") int characteristicId
  );
}
