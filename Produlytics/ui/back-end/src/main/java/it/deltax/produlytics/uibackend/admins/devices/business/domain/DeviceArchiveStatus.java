package it.deltax.produlytics.uibackend.admins.devices.business.domain;

/**
 * Record che rappresenta una macchina a cui è stato aggiornato lo stato di archiviazione
 */
public record DeviceArchiveStatus(
	int id,
	boolean archived
) {
}
