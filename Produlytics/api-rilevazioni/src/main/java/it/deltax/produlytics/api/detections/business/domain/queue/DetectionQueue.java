package it.deltax.produlytics.api.detections.business.domain.queue;

import it.deltax.produlytics.api.detections.business.domain.Detection;

// Si occupa di accodare una rilevazione autenticata per analizzarla in futuro.
// L'operazione non è bloccante.
public interface DetectionQueue {
	void enqueueDetection(Detection detection);
}
