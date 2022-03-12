package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Utils;

import java.util.List;

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

		for(MarkableDetection detection : detections) {
			double value = detection.getValue();
			if(lowerZone < value && value < upperZone) {
				return;
			}
		}

		detections.forEach(MarkableDetection::markOutlier);
	}
}
