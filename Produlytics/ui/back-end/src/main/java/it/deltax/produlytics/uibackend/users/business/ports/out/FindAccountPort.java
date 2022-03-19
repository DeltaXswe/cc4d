package it.deltax.produlytics.uibackend.users.business.ports.out;

import it.deltax.produlytics.uibackend.users.business.domain.Account;

import java.util.Optional;

public interface FindAccountPort {
    Optional<Account> findByUsername(String username);
}