package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

public class CachedDetection {
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

	boolean isSameDetection(Detection detection) {
		return this.detection.characteristicId() == detection.characteristicId()
			&& this.detection.creationTime() == detection.creationTime();
	}
}
