package it.deltax.produlytics.uibackend.admins.adapters;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.ModDevArchStatusPort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.ModifyDevicePort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.UpdateAccountAdminPort;
import it.deltax.produlytics.uibackend.repositories.AdminRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminAdapter implements UpdateAccountAdminPort,
	FindAccountPort,
	InsertAccountPort,
	ModifyDevicePort,
	ModDevArchStatusPort
{

	private final AdminRepository repo;

	public AdminAdapter(AdminRepository repo) {this.repo = repo; }

	@Override
	public int updateAccount(String username, String hashedPassword, boolean administrator){
		return repo.updateAccount(username, hashedPassword, administrator);
	}

	@Override
	public int updateAccountPrivileges(String username, boolean administrator){
		return repo.updateAccountPrivileges(username, administrator);
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
	public void insertAccount(String username, String hashedPassword, boolean administrator){
		repo.insertAccount(username, hashedPassword, administrator, false);
	}

	@Override
	public int modifyDevicePort(int deviceId, String name){
		return repo.updateDeviceName(deviceId, name);
	}

	@Override
	public int modifyDeviceArchivedStatus(int deviceId, boolean archived) {
		return repo.updateDeviceArchivedStatus(deviceId, archived);
	}
}
