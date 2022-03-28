package it.deltax.produlytics.uibackend.admins.business.ports.out;

public interface ModDevArchStatusPort {
	int modifyDeviceArchivedStatus(int deviceId, boolean archived);
}
