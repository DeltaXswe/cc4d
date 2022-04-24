package it.deltax.produlytics.uibackend.admins.devices.business.domain;

/**
 * Record che rappresenta una macchina a cui è stato aggiornato lo stato di attivazione
 */
public record DeviceDeactivateStatus(
	int id,
	boolean deactivated
) {
}
