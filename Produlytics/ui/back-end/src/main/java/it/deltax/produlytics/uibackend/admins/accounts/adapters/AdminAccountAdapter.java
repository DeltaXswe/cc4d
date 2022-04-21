package it.deltax.produlytics.uibackend.admins.accounts.adapters;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.TinyAccount;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetTinyAccountsPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountArchiveStatusPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * L'adapter dello strato di persistenza per le operazioni svolte dagli amministratori sugli utenti
 */
@Component
public class AdminAccountAdapter implements UpdateAccountArchiveStatusPort,
	UpdateAccountByAdminPort,
	FindAccountPort,
	InsertAccountPort,
	GetTinyAccountsPort
{
	private final AccountRepository repo;


	/**
	 * Il costruttore
	 * @param repo lo strato di persistenza con i dati sugli utenti
	 */
	public AdminAccountAdapter(AccountRepository repo) {this.repo = repo; }


	/**
	 * Aggiorna lo stato di archiviazione dell'utente dato nello strato di persistenza
	 * @param account l'utente con lo stato di archiviazione aggiornato da memorizzare
	 */
	@Override
	public void updateAccountArchiveStatus(Account account){
		this.repo.save(new AccountEntity(
			account.username(),
			account.hashedPassword(),
			account.administrator(),
			account.archived())
		);
	}

	/**
	 * Trova nello strato di persistenza un utente, dato il suo username
	 * @param username l'username dell'utente da trovare
	 * @return l'utente, se trovato; Optional vuoto, altrimenti
	 */
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

	/**
	 * Aggiorna un utente nello strato di persistenza
	 * @param account l'utente da memorizzare con la password aggiornata
	 */
	@Override
	public void updateAccount(Account account){
		this.repo.save(new AccountEntity(
			account.username(),
			account.hashedPassword(),
			account.administrator(),
			account.archived())
		);
	}

	/**
	 * Inserisce un utente nello strato di persistenza
	 * @param account l'utente da inserire
	 */
	@Override
	public void insertAccount(Account account){
		this.repo.save(new AccountEntity(
				account.username(),
				account.hashedPassword(),
				account.administrator(),
				account.archived()
			)
		);
	}

	/**
	 * Restituisce la lista di tutti gli utenti presenti nello strato di persistenza
	 * @return la lista degli utenti, ciascuno con username, permessi e stato di archiviazione
	 */
	@Override
	public List<TinyAccount> getTinyAccounts() {
		return StreamSupport.stream(this.repo.findAll().spliterator(), false)
			.map(account ->
				new TinyAccount(account.getUsername(), account.getAdministrator(), account.getArchived())
			)
			.collect(Collectors.toList());
	}
}
