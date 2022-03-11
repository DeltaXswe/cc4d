package it.deltax.produlytics.api.detections.business.domain.queue;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

public class DetectionSerieMarkOutlierAdapter implements MarkOutlierPort {
	private final DetectionSerie detectionSerie;

	DetectionSerieMarkOutlierAdapter(DetectionSerie detectionSerie) {
		this.detectionSerie = detectionSerie;
	}

	@Override
	public void markOutlier(Detection detection) {
		this.detectionSerie.markOutlier(detection);
	}
}
