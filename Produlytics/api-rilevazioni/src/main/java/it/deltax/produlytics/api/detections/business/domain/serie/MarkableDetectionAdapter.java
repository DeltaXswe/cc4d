package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.MarkableDetection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

public class MarkableDetectionAdapter implements MarkableDetection {
	private final MarkOutlierPort markOutlierPort;
	private final Detection detection;

	public MarkableDetectionAdapter(MarkOutlierPort markOutlierPort, Detection detection) {
		this.markOutlierPort = markOutlierPort;
		this.detection = detection;
	}
	@Override
	public double value() {
		return this.detection.value();
	}

	@Override
	public void markOutlier() {
		this.markOutlierPort.markOutlier(this.detection);
	}
}
