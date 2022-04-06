package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.2.
// Identifica se 2 punti su 3 consecutivi sono all'interno di una delle due zone A o oltre.
public class ControlChartZoneA implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 3;
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> detections, ControlLimits limits) {
		double lowerZone = limits.lowerABLimit();
		long inLowerZone = detections.stream().filter(detection -> detection.value() < lowerZone).count();

		double upperZone = limits.upperABLimit();
		long inUpperZone = detections.stream().filter(detection -> detection.value() > upperZone).count();

		if(inLowerZone >= 2 || inUpperZone >= 2) {
			ControlChartUtils.markAll(detections);
		}
	}
}
