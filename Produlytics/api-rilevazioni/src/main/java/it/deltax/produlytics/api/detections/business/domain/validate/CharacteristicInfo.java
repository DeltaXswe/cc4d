package it.deltax.produlytics.api.detections.business.domain.validate;

/**
 * Questo record rappresenta le informazioni di una caratteristica utili alla validazion
 * di una rilevazione.
 *
 * @param characteristicId l'identificativo della caratteristica all'interno della sua macchina
 * @param characteristicId l'identificativo della caratteristica all'interno della sua macchina
 */
public record CharacteristicInfo(int characteristicId, boolean archived) {}
