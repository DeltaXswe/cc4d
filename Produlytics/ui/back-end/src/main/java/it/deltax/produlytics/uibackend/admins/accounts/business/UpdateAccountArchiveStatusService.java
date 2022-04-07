package it.deltax.produlytics.uibackend.admins.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountArchiveStatusPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccountArchiveStatusService implements UpdateAccountArchiveStatusUseCase {
	private final UpdateAccountArchiveStatusPort updateAccountArchiveStatusPort;
	private final FindAccountPort findAccountPort;

	public UpdateAccountArchiveStatusService(
		UpdateAccountArchiveStatusPort updateAccountArchiveStatusPort, FindAccountPort findAccountPort) {
		this.updateAccountArchiveStatusPort = updateAccountArchiveStatusPort;
		this.findAccountPort = findAccountPort;
	}

	@Override
	public void updateAccountArchiveStatus(AccountArchiveStatus command) throws BusinessException {
		Account.AccountBuilder toUpdate = findAccountPort.findByUsername(command.username())
			.map(account -> account.toBuilder())
			.orElseThrow(() -> new BusinessException("accountNotFound", ErrorType.NOT_FOUND));

		toUpdate.withArchived(command.archived());
		updateAccountArchiveStatusPort.updateAccountArchiveStatus(toUpdate.build());
	}
}
