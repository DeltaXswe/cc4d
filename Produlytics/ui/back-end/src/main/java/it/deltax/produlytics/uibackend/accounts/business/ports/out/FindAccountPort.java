package it.deltax.produlytics.uibackend.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

import java.util.Optional;

/**
 * La porta per cercare un utente dato l'username
 */
public interface FindAccountPort {
    Optional<Account> findByUsername(String username);
}