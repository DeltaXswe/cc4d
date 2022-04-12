package it.deltax.produlytics.uibackend.admins.devices.business.domain;

/**
 * Record che rappresenta una caratteristica a cui è stato aggiornato lo stato di archiviazione
 * @author Alberto Lazari
 */
public record CharacteristicArchiveStatus(
	int id,
	int deviceId,
	boolean archived
) {}
