package it.deltax.produlytics.api.detections.business.domain.validate;

/**
 * Questo record rappresenta le informazioni di una macchina utili alla
 * validazione di una rilevazione.
 *
 * @param deviceId L'identificativo della macchina.
 * @param archived `true` se la macchina è archiviata, `false` altrimenti.
 * @param deactivated `true` se la macchina è disattivata, `false` altrimenti.
 */
public record DeviceInfo(int deviceId, boolean archived, boolean deactivated) {}
