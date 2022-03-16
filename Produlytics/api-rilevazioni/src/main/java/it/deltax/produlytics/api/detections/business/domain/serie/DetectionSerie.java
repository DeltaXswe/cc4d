package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.Detection;

// Rappresenta una serie di rilevazioni che si occupa di memorizzare
// nuove rilevazioni e identificare quelle anomale.
public interface DetectionSerie {
	void insertDetection(Detection rawDetection);
}
