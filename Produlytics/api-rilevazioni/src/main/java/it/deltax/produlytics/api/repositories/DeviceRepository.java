package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Questa interfaccia espone le query che è possibile effettuare sul database, in particolare sulla
 * tabella {@code device}, lasciando a Spring il compito d'implementarla.
 */
@Repository
@SuppressWarnings("unused")
public interface DeviceRepository extends CrudRepository<DeviceEntity, Integer> {
  /**
   * Questo metodo si occupa di trovare una macchina data la sua chiave API.
   *
   * @param apiKey la chiave API della macchina da trovare
   * @return ritorna un entità rappresentante la macchina, se esiste, altrimenti ritorna
   *     {@code Optional.empty()}
   */
  Optional<DeviceEntity> findByApiKey(String apiKey);
}
