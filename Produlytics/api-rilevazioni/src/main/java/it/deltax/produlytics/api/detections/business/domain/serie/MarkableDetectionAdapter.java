package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

// Adapter da Detection e MarkOutlierPort a MarkableDetection.
public class MarkableDetectionAdapter implements MarkableDetection {
	private final SeriePortFacade seriePortFacade;
	private final Detection detection;

	public MarkableDetectionAdapter(SeriePortFacade seriePortFacade, Detection detection) {
		this.seriePortFacade = seriePortFacade;
		this.detection = detection;
	}

	@Override
	public double value() {
		return this.detection.value();
	}

	@Override
	public void markOutlier() {
		this.seriePortFacade.markOutlier(this.detection);
	}
}
