package it.deltax.produlytics.api.detections.business.domain;

/**
 * Questo record rappresenta una rilevazione in arrivo da processare.
 *
 * @param apiKey la chiave API inviata dal client;
 * @param characteristic il nome della caratteristica a cui appartiene la rilevazione da processare;
 * @param value il valore numerico della rilevazione.
 */
public record IncomingDetection(String apiKey, String characteristic, double value) {}
