package it.deltax.produlytics.uibackend.db.configurazione.repositories;

import it.deltax.produlytics.persistence.configurazione.Macchina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MacchinaRepository extends CrudRepository<Macchina, Long> {
}
