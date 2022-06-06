package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.AccountEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Rappresenta il repository degli utenti. */
@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, String> {
    List<AccountEntity> findAllByOrderByUsername();
}
