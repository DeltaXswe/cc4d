package it.deltax.produlytics.api.detections.business.domain;

// Rilevazione in arrivo da una macchina che non è stata ancora riconosciuta.
public record IncomingDetection(String apiKey, String characteristic, double value) {}
