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
   * Questo metodo si occupa di cercare una caratteristica dato il suo identificativo globale.
   *
   * @param deviceId l'identificativo della macchina, che deve esistere, a cui appartiene la
   *     caratteristica da cercare
   * @param characteristicId l'identificativo della caratteristica all'interno della macchina
   * @return ritorna un entità rappresentante la caratteristica
   */
  CharacteristicEntity findByDeviceIdAndId(int deviceId, int characteristicId);

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
      FROM (
          SELECT * FROM detection
          WHERE device_id = :deviceId AND characteristic_id = :characteristicId
          ORDER BY creation_time DESC
          LIMIT :sampleSize
      ) helper
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
