package it.deltax.produlytics.api.detections.business.domain.validate;

/**
 * Questo record rappresenta le informazioni di una macchina utili alla
 * validazione di una rilevazione.
 *
 * @param deviceId l'identificativo della macchina
 * @param deviceId l'identificativo della macchina
 * @param deviceId l'identificativo della macchina
 */
public record DeviceInfo(int deviceId, boolean archived, boolean deactivated) {}
