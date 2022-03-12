package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Utils;

import java.util.List;

public class ControlChartZoneB implements ControlChart {
	@Override
	public void analyzeDetection(
		List<MarkableDetection> lastDetections, Limits limits
	) {
		if(lastDetections.size() < 5) {
			return;
		}
		List<MarkableDetection> detections = Utils.lastN(lastDetections, 5);

		if(limits.calculatedLimits().isEmpty()) {
			return;
		}
		CalculatedLimits calculatedLimits = limits.calculatedLimits().get();

		double lowerZone = calculatedLimits.mean() - calculatedLimits.deviation();
		long inLowerZone = detections.stream().filter(detection -> detection.getValue() < lowerZone).count();
		double upperZone = calculatedLimits.mean() + calculatedLimits.deviation();
		long inUpperZone = detections.stream().filter(detection -> detection.getValue() > upperZone).count();

		if(inLowerZone >= 4 || inUpperZone >= 4) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
