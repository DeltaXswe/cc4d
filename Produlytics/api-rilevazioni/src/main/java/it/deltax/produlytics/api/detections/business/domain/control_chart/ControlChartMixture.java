package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Utils;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.6.
// Identifica se 8 punti consecutivi non appartengono alle zone C.
public class ControlChartMixture implements ControlChart {
	@Override
	public void analyzeDetection(
		List<MarkableDetection> lastDetections, Limits limits
	) {
		if(lastDetections.size() < 8) {
			return;
		}
		List<MarkableDetection> detections = Utils.lastN(lastDetections, 8);

		if(limits.calculatedLimits().isEmpty()) {
			return;
		}
		CalculatedLimits calculatedLimits = limits.calculatedLimits().get();

		double lowerZone = calculatedLimits.mean() - calculatedLimits.deviation();
		double upperZone = calculatedLimits.mean() + calculatedLimits.deviation();

		boolean allOutside = detections.stream()
			.map(MarkableDetection::getValue)
			.allMatch(value -> value < lowerZone || value > upperZone);
		if(allOutside) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
