package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Rappresenta il repository degli utenti
 * @author Leila Dardouri
 */
@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, String> {
}