package it.deltax.produlytics.uibackend.admins.accounts.adapters;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetAccountsPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountArchiveStatusPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AdminAccountAdapter implements UpdateAccountArchiveStatusPort,
	UpdateAccountByAdminPort,
	FindAccountPort,
	InsertAccountPort,
	GetAccountsPort
{
	private final AccountRepository repo;

	public AdminAccountAdapter(AccountRepository repo) {this.repo = repo; }

	@Override
	public void updateAccountArchiveStatus(Account account){
		repo.save(new AccountEntity(
			account.username(),
			account.hashedPassword(),
			account.administrator(),
			account.archived())
		);
	}

	@Override
	public Optional<Account> findByUsername(String username) {
		return repo.findById(username)
			.map(utente ->
				new Account(
					utente.getUsername(),
					utente.getHashedPassword(),
					utente.getAdministrator(),
					utente.getArchived())
			);
	}
	@Override
	public void updateAccount(Account account){
		repo.save(new AccountEntity(
			account.username(),
			account.hashedPassword(),
			account.administrator(),
			account.archived())
		);
	}

	@Override
	public void insertAccount(Account account){
		repo.save(new AccountEntity(
				account.username(),
				account.hashedPassword(),
				account.administrator(),
				account.archived()
			)
		);
	}

	@Override
	public List<AccountTiny> getAccounts() {
		return StreamSupport.stream(repo.findAll().spliterator(), false)
			.map(account ->
				new AccountTiny(account.getUsername(), account.getAdministrator(), account.getArchived())
			)
			.collect(Collectors.toList());
	}
}
