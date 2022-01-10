package it.deltax.uibackend.db.repos;

import it.deltax.uibackend.db.model.Macchina;
import org.springframework.data.repository.CrudRepository;

public interface MacchinaRepository extends CrudRepository<Macchina, String> {
}
