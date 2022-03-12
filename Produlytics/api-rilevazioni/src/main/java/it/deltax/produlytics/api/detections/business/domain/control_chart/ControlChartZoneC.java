package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Utils;

import java.util.List;

public class ControlChartZoneC implements ControlChart {
	@Override
	public void analyzeDetection(
		List<MarkableDetection> lastDetections, Limits limits
	) {
		if(lastDetections.size() < 7) {
			return;
		}
		List<MarkableDetection> detections = Utils.lastN(lastDetections, 7);

		if(limits.calculatedLimits().isEmpty()) {
			return;
		}
		CalculatedLimits calculatedLimits = limits.calculatedLimits().get();

		// TODO: Questo è un po' stupido
		double mean = calculatedLimits.mean();
		long inLowerZone = detections.stream().filter(detection -> detection.getValue() < mean).count();
		long inUpperZone = detections.stream().filter(detection -> detection.getValue() > mean).count();

		if(inLowerZone >= 7 || inUpperZone >= 7) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
