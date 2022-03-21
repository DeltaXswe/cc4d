package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PwdEncrypterPort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.UpdateAccountAdminPort;
import it.deltax.produlytics.uibackend.admins.business.ports.in.ChangeAccountAdminUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangeAccountAdminService implements ChangeAccountAdminUseCase {
	private final UpdateAccountAdminPort updateAccountAdminPort;
	private final FindAccountPort findAccountPort;
	private final PwdEncrypterPort pwdEncrypterPort;

	public ChangeAccountAdminService(
		UpdateAccountAdminPort updateAccountAdminPort,
		FindAccountPort findUserPort,
		PwdEncrypterPort pwdEncrypterPort){
		this.updateAccountAdminPort = updateAccountAdminPort;
		this.findAccountPort = findUserPort;
		this.pwdEncrypterPort = pwdEncrypterPort;
	}

	@Override
	public boolean changeByUsername(String username, String newPassword, boolean administrator){
		Optional<Account> result = findAccountPort.findByUsername(username); //cerco utente per username
		if(result.isPresent()){ //se esiste
			if(!newPassword.isEmpty()) { //se la password non è vuota cambio pwd e permessi
				String encryptedNew = pwdEncrypterPort.encrypt(newPassword);
				if(updateAccountAdminPort.updateAccount(username, encryptedNew, administrator)>0)
					return true;
			}
			if (result.get().admin()!=administrator) //se la password è vuota allora controllo se ha cambiato i permessi
				if(updateAccountAdminPort.updateAccountPrivileges(username, administrator)>0) //aggiorno i permessi
					return true;
		}
		return false;
	}
}