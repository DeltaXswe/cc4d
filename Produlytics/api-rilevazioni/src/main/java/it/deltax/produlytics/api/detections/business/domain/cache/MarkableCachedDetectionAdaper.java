package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.control_chart.MarkableDetection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

public class MarkableCachedDetectionAdaper implements MarkableDetection {
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
	public void mark() {
		this.cachedDetection.mark(markOutlierPort);
	}
}