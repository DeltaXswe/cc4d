package it.deltax.produlytics.uibackend.admins.business.ports.in;

public interface InsertAccountUseCase {
	boolean insertAccount(String username, String password, boolean administrator);
}