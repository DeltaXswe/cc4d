package it.deltax.produlytics.uibackend.admins.business.ports.in;

public interface ModDevArchStatusUseCase {
	boolean modDevArchStatus(int deviceId, boolean archived);
}
