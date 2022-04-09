package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartUtils;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.4.
// Identifica se 7 punti consecutivi sono dallo stesso lato rispetto alla media.
public class ControlChartZoneC implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 7;
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> detections, ControlLimits limits) {
		double mean = limits.mean();
		long inLowerZone = detections.stream().filter(detection -> detection.value() < mean).count();
		long inUpperZone = detections.stream().filter(detection -> detection.value() > mean).count();

		if(inLowerZone >= 7 || inUpperZone >= 7) {
			ControlChartUtils.markAll(detections);
		}
	}
}