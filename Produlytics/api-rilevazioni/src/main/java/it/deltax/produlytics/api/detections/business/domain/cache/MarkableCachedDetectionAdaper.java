package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.control_chart.MarkableDetection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

// Adatta una `CachedDetection` e una `MarkOutlierPort` a `MarkableDetection`,
// inoltrando a `CachedDetection::mark` le richieste di marcare come anomalie.
class MarkableCachedDetectionAdaper implements MarkableDetection {
	private final CachedDetection cachedDetection;
	private final MarkOutlierPort markOutlierPort;

	MarkableCachedDetectionAdaper(CachedDetection cachedDetection, MarkOutlierPort markOutlierPort) {
		this.cachedDetection = cachedDetection;
		this.markOutlierPort = markOutlierPort;
	}

	@Override
	public double getValue() {
		return cachedDetection.toDetection().value();
	}

	@Override
	public void markOutlier() {
		this.cachedDetection.mark(markOutlierPort);
	}
}
