package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.1.
// Identifica i punti oltre i limiti di controllo.
public class ControlChartBeyondLimits implements ControlChart {
	@Override
	public void analyzeDetection(
		List<MarkableDetection> lastDetections, Limits limits
	) {
		if(lastDetections.size() < 1) {
			return;
		}
		MarkableDetection detection = lastDetections.get(0);

		if(limits.calculatedLimits().isEmpty()) {
			return;
		}
		CalculatedLimits calculatedLimits = limits.calculatedLimits().get();

		double upperControlLimit = calculatedLimits.mean() + 3 * calculatedLimits.deviation();
		double lowerControlLimit = calculatedLimits.mean() - 3 * calculatedLimits.deviation();

		if(detection.getValue() > upperControlLimit || detection.getValue() < lowerControlLimit) {
			detection.markOutlier();
		}
	}
}
