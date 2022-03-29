package it.deltax.produlytics.uibackend.admins.business.ports.out;

public interface UpdateAccountAdminPort {
	int updateAccount(String username, String hashedPassword, boolean administrator);
	int updateAccountPrivileges(String username, boolean administrator);
}