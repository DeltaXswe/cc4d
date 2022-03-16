package it.deltax.produlytics.api.detections.business.domain;

// Informazioni di una macchina utili per la validazione.
public record DeviceInfo(int deviceId, boolean archived, boolean deactivated) {}
