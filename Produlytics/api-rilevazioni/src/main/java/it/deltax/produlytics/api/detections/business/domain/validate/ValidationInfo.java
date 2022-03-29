package it.deltax.produlytics.api.detections.business.domain.validate;

// Dati ritornati dal validatore delle richieste.
// Per ora include solo l'identificativo della macchina ottenuto a partire dalla chiave API.
public record ValidationInfo(int deviceId) {}
