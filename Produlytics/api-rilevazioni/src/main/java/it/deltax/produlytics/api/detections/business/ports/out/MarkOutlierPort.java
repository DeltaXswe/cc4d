package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.Detection;

// Marca una rilevazione esistente come anomala.
public interface MarkOutlierPort {
	void markOutlier(Detection detection);
}
