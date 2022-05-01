package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** L'adapter dello strato di persistenza per le operazioni svolte dagli utenti. */
@Component
public class AccountAdapter implements FindAccountPort, UpdateAccountPasswordPort {
  private final AccountRepository repo;

  /**
   * Il costruttore.
   *
   * @param repo lo strato di persistenza con i dati sugli utenti
   */
  public AccountAdapter(AccountRepository repo) {
    this.repo = repo;
  }

  /**
   * Trova nello strato di persistenza un utente, dato il suo username.
   *
   * @param username l'username dell'utente da trovare
   * @return l'utente, se trovato; Optional vuoto, altrimenti
   */
  @Override
  public Optional<Account> findByUsername(String username) {
    return this.repo
        .findById(username)
        .map(
            utente ->
                new Account(
                    utente.getUsername(),
                    utente.getHashedPassword(),
                    utente.getAdministrator(),
                    utente.getArchived()));
  }

  /**
   * Aggiorna la password di un utente nello strato di persistenza.
   *
   * @param account l'utente da memorizzare con la password aggiornata
   */
  @Override
  public void updateAccountPassword(Account account) {
    this.repo.save(
        new AccountEntity(
            account.username(),
            account.hashedPassword(),
            account.administrator(),
            account.archived()));
  }
}
