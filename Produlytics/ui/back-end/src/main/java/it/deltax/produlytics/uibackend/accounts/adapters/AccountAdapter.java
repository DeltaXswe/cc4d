package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPort;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountAdapter implements UpdateAccountPort, FindAccountPort {
    private final AccountRepository repo;

    public AccountAdapter(AccountRepository repo) {this.repo = repo; }

    @Override
    public boolean updateAccount(String username, String hashedPassword){
        return repo.updateAccount(username, hashedPassword) > 0;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return repo.findByUsername(username)
            .map(utente ->
                        new Account(
                                utente.getUsername(),
                                utente.getHashedPassword(),
                                utente.getAdministrator(),
                                utente.getArchived())
                        );
    }
}