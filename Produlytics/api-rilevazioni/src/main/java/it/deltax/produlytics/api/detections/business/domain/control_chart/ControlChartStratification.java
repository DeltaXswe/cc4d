package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Utils;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.7.
// Identifica se 15 punti consecutivi appartengono alle zone C.
public class ControlChartStratification implements ControlChart {
	@Override
	public void analyzeDetection(
		List<MarkableDetection> lastDetections, Limits limits
	) {
		if(lastDetections.size() < 15) {
			return;
		}
		List<MarkableDetection> detections = Utils.lastN(lastDetections, 15);

		if(limits.calculatedLimits().isEmpty()) {
			return;
		}
		CalculatedLimits calculatedLimits = limits.calculatedLimits().get();

		double lowerZone = calculatedLimits.mean() - calculatedLimits.deviation();
		double upperZone = calculatedLimits.mean() + calculatedLimits.deviation();

		boolean allInside = detections.stream()
			.map(MarkableDetection::getValue)
			.allMatch(value -> lowerZone < value && value < upperZone);
		if(allInside) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
