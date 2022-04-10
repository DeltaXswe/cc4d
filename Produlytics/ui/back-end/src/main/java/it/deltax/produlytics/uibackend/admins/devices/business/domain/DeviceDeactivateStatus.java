package it.deltax.produlytics.uibackend.admins.devices.business.domain;

/**
 * Il record rappresenta una macchina a cui è stato aggiornato lo stato di attivazione
 * @author Leila Dardouri
 */
public record DeviceDeactivateStatus(
	int id,
	boolean deactivated
) {
}
