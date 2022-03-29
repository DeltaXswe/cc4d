package it.deltax.produlytics.uibackend.admins.business.ports.out;

public interface InsertAccountPort {
	void insertAccount(String username, String hashedPassword, boolean administrator);
}
