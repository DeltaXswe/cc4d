package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.RawDetection;

// Rappresenta una serie di rilevazioni che si occupa di memorizzare
// nuove rilevazioni e identificare quelle anomale.
public interface DetectionSerie {
	void insertDetection(RawDetection rawDetection);
}
