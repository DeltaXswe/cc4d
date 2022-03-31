package it.deltax.produlytics.uibackend.admins.adapters;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.UpdateDeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.business.ports.out.UpdateDeviceNamePort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.repositories.AdminRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminAdapter implements UpdateAccountByAdminPort,
	FindAccountPort,
	InsertAccountPort, UpdateDeviceNamePort, UpdateDeviceArchiveStatus
{

	private final AdminRepository repo;

	public AdminAdapter(AdminRepository repo) {this.repo = repo; }

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
	public void updateDeviceNamePort(int deviceId, String name){
		repo.updateDeviceName(deviceId, name);
	}

	@Override
	public void updateDeviceArchiveStatus(int deviceId, boolean archived) {
		repo.updateDeviceArchivedStatus(deviceId, archived);
	}
}
