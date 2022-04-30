package it.deltax.produlytics.api.detections.business.domain;

/**
 * Questo record rappresenta l'identificativo globale di una caratteristica.
 *
 * @param deviceId l'identificativo della macchina a cui appartiene la caratteristica
 * @param deviceId l'identificativo della macchina a cui appartiene la caratteristica
 */
public record CharacteristicId(int deviceId, int characteristicId) {}
