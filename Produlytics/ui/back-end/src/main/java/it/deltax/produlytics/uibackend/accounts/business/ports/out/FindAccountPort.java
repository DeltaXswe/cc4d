package it.deltax.produlytics.uibackend.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

import java.util.Optional;

public interface FindAccountPort {
    Optional<Account> findByUsername(String username);
}