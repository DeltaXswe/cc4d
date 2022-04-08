package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.1.
// Identifica i punti oltre i limiti di controllo.
public class ControlChartBeyondLimits implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 1;
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> lastDetections, ControlLimits limits) {
		MarkableDetection detection = lastDetections.get(0);

		double lowerControlLimit = limits.lowerLimit();
		double upperControlLimit = limits.upperLimit();

		if(detection.value() < lowerControlLimit || detection.value() > upperControlLimit) {
			detection.markOutlier();
		}
	}
}
