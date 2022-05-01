package it.deltax.produlytics.uibackend.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import java.util.Optional;

/** Interfaccia che modella il caso d'uso della ricerca di un utente. */
public interface FindAccountUseCase {
  Optional<Account> findByUsername(String username) throws BusinessException;
}
