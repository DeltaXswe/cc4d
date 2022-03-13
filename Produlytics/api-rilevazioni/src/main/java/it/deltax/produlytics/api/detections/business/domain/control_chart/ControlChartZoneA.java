package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Utils;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.2.
// Identifica se 2 punti su 3 consecutivi sono all'interno di una delle due zone A o oltre.
public class ControlChartZoneA implements ControlChart {
	@Override
	public void analyzeDetection(
		List<MarkableDetection> lastDetections, Limits limits
	) {
		if(lastDetections.size() < 3) {
			return;
		}
		List<MarkableDetection> detections = Utils.lastN(lastDetections, 3);

		if(limits.calculatedLimits().isEmpty()) {
			return;
		}
		CalculatedLimits calculatedLimits = limits.calculatedLimits().get();

		double lowerZone = calculatedLimits.mean() - 2 * calculatedLimits.deviation();
		long inLowerZone = detections.stream().filter(detection -> detection.getValue() < lowerZone).count();
		double upperZone = calculatedLimits.mean() + 2 * calculatedLimits.deviation();
		long inUpperZone = detections.stream().filter(detection -> detection.getValue() > upperZone).count();

		if(inLowerZone >= 2 || inUpperZone >= 2) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
