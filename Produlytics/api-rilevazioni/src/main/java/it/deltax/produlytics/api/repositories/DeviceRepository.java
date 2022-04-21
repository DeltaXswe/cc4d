package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Questa interfaccia espone le query che è possibile effettuare sul database, in particolare sulla
 * tabella `device`, lasciando a Spring il compito d'implementarla.
 */
@Repository
@SuppressWarnings("unused")
public interface DeviceRepository extends CrudRepository<DeviceEntity, Integer> {
  /**
   * Questo metodo si occupa di trovare una macchina data la sua chiave API.
   *
   * @param apiKey La chiave API della macchina da trovare.
   * @return Ritorna un entità rappresentante la macchina, se esiste, altrimenti ritorna
   *     `Optional.empty()`.
   */
  Optional<DeviceEntity> findByApiKey(String apiKey);
}
