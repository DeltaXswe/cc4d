package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.admins.business.domain.UpdateAdminAccout;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateAccountByAdminUseCase {
	void updateByUsername(UpdateAdminAccout command) throws BusinessException;
}
