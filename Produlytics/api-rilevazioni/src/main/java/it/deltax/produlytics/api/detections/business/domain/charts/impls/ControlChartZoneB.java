package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.3.
// Identifica se 4 punti su 5 consecutivi sono all'interno di una delle due zone B o oltre.
public class ControlChartZoneB implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 5;
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> detections, ControlLimits limits) {
		double lowerZone = limits.lowerBCLimit();
		long inLowerZone = detections.stream().filter(detection -> detection.value() < lowerZone).count();

		double upperZone = limits.upperBCLimit();
		long inUpperZone = detections.stream().filter(detection -> detection.value() > upperZone).count();

		if(inLowerZone >= 4 || inUpperZone >= 4) {
			ControlChartUtils.markAll(detections);
		}
	}
}
