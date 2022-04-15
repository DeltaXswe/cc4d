package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * L'adapter dello strato di persistenza per le operazioni svolte dagli utenti
 */
@Component
public class AccountAdapter implements FindAccountPort,
    UpdateAccountPasswordPort {
    private final AccountRepository repo;

    public AccountAdapter(AccountRepository repo) {this.repo = repo; }

    @Override
    public Optional<Account> findByUsername(String username) {
        return this.repo.findById(username)
            .map(utente ->
                new Account(
                    utente.getUsername(),
                    utente.getHashedPassword(),
                    utente.getAdministrator(),
                    utente.getArchived())
            );
    }

    @Override
    public void updateAccountPassword(Account account){
        this.repo.save(new AccountEntity(
            account.username(),
            account.hashedPassword(),
            account.administrator(),
            account.archived()
        ));
    }
}