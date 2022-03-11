package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

public class CachedDetectionSerieMarkOutlierAdapter implements MarkOutlierPort {
	private final CachedDetectionSerie cachedDetectionSerie;

	public CachedDetectionSerieMarkOutlierAdapter(CachedDetectionSerie cachedDetectionSerie) {
		this.cachedDetectionSerie = cachedDetectionSerie;
	}

	@Override
	public void markOutlier(Detection detection) {
		this.cachedDetectionSerie.markOutlier(detection);
	}
}
