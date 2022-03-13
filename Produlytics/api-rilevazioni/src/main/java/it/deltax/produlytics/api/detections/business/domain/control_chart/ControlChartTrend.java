package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Utils;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.5.
// Identifica se 7 punti consecutivi seguono lo stesso ordine.
public class ControlChartTrend implements ControlChart {
	@Override
	public void analyzeDetection(List<MarkableDetection> lastDetections, Limits limits) {
		if(lastDetections.size() < 7) {
			return;
		}
		List<MarkableDetection> detections = Utils.lastN(lastDetections, 7);

		boolean allIncreasing = Utils.windows(detections, 2)
			.allMatch(window -> window.get(0).getValue() <= window.get(1).getValue());
		boolean allDecreasing = Utils.windows(detections, 2)
			.allMatch(window -> window.get(0).getValue() >= window.get(1).getValue());

		if(allIncreasing || allDecreasing) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
