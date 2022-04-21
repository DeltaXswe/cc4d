package it.deltax.produlytics.api.detections.business.domain.validate;

/**
 * Questo record rappresenta le informazioni di una caratteristica utili alla validazione di una rilevazione.
 *
 * @param characteristicId L'identificativo della caratteristica all'interno della sua macchina.
 * @param archived `true` se la caratteristica è archiviata, `false` altrimenti.
 */
public record CharacteristicInfo(int characteristicId, boolean archived) {}
