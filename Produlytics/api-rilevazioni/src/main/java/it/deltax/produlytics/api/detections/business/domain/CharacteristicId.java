package it.deltax.produlytics.api.detections.business.domain;

/**
 * Questo record rappresenta l'identificativo globale di una caratteristica.
 *
 * @param deviceId L'identificativo della macchina a cui appartiene la caratteristica.
 * @param characteristicId L'identificativo della caratteristica all'interno della macchina.
 */
public record CharacteristicId(int deviceId, int characteristicId) {}
