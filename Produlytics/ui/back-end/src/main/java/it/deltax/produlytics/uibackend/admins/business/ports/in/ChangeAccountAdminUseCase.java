package it.deltax.produlytics.uibackend.admins.business.ports.in;

public interface ChangeAccountAdminUseCase {
	boolean changeByUsername(String username, String newPassword, boolean administrator);
}
