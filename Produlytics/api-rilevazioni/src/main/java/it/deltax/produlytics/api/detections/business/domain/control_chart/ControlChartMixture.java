package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.MarkableDetection;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.6.
// Identifica se 8 punti consecutivi non appartengono alle zone C.
public class ControlChartMixture implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 8;
	}

	@Override
	public void analyzeDetections(List<MarkableDetection> detections, ControlLimits limits) {
		double lowerZone = limits.lowerBCLimit();
		double upperZone = limits.upperBCLimit();

		boolean allOutside = detections.stream()
			.map(MarkableDetection::value)
			.allMatch(value -> value < lowerZone || value > upperZone);
		if(allOutside) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}