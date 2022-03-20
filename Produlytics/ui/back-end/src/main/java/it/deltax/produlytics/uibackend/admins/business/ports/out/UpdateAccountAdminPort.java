package it.deltax.produlytics.uibackend.admins.business.ports.out;

public interface UpdateAccountAdminPort {
	boolean updateAccount(String username, String hashedPassword, boolean administrator);
	boolean updateAccountPrivileges(String username, boolean administrator);
}