package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.7.
// Identifica se 15 punti consecutivi appartengono alle zone C.
public class ControlChartStratification implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 15;
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> detections, ControlLimits limits) {
		double lowerZone = limits.lowerBCLimit();
		double upperZone = limits.upperBCLimit();

		boolean allInside = detections.stream()
			.map(MarkableDetection::value)
			.allMatch(value -> lowerZone < value && value < upperZone);
		if(allInside) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
