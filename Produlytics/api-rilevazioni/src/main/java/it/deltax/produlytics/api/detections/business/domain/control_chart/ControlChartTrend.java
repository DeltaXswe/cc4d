package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.MarkableDetection;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.5.
// Identifica se 7 punti consecutivi seguono lo stesso ordine.
public class ControlChartTrend implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 7;
	}

	@Override
	public void analyzeDetections(List<MarkableDetection> detections, ControlLimits limits) {
		boolean allIncreasing = Utils.windows(detections, 2)
			.allMatch(window -> window.get(0).value() <= window.get(1).value());
		boolean allDecreasing = Utils.windows(detections, 2)
			.allMatch(window -> window.get(0).value() >= window.get(1).value());

		if(allIncreasing || allDecreasing) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
