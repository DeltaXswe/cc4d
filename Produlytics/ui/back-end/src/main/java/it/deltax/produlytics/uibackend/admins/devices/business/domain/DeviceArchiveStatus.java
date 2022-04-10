package it.deltax.produlytics.uibackend.admins.devices.business.domain;

/**
 * Il record rappresenta una macchina a cui è stato aggiornato lo stato di archiviazione
 * @author Leila Dardouri
 */
public record DeviceArchiveStatus(
	int id,
	boolean archived
) {
}
