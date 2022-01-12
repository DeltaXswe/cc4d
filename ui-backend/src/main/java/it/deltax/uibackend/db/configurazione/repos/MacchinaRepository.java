package it.deltax.uibackend.db.configurazione.repos;

import it.deltax.uibackend.db.configurazione.model.Macchina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MacchinaRepository extends CrudRepository<Macchina, Long> {
}
