package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

// Rappresenta una rilevazione memorizzata nella cache di `CachedDetectionSerie`.
// Questa classe soddisfa due scopi:
// - permette di modificare una `Detection`, che essendo un record non è modificabile.
// - evita di ri-marcare rilevazioni già anomale.
class CachedDetection {
	private Detection detection;

	CachedDetection(Detection detection) {
		this.detection = detection;
	}

	Detection toDetection() {
		return this.detection;
	}

	void mark(MarkOutlierPort markOutlierPort) {
		if(!this.detection.outlier()) {
			this.detection = new Detection(
				this.detection.deviceId(),
				this.detection.characteristicId(),
				this.detection.creationTime(),
				this.detection.value(),
				true
			);
			markOutlierPort.markOutlier(this.detection);
		}
	}
}
