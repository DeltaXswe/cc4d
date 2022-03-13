package it.deltax.produlytics.api.detections.business.domain;

// Una rilevazione in arrivo che non è stata ancora processata dalle carte di controllo.
// Contiene anche le informazioni attuali della caratteristica necessarie per calcolare i limiti.
public record RawDetection(
	int deviceId, int characteristicId, java.time.Instant creationTime, double value, LimitsInfo limitsInfo
) {}
