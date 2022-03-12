package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Utils;

import java.util.List;

public class ControlChartTrend implements ControlChart {
	@Override
	public void analyzeDetection(List<MarkableDetection> lastDetections, Limits limits) {
		if(lastDetections.size() < 7) {
			return;
		}
		List<MarkableDetection> detections = Utils.lastN(lastDetections, 7);

		boolean sameTrend = Utils.windows(detections, 3).allMatch(window -> {
			double w0 = window.get(0).getValue();
			double w1 = window.get(1).getValue();
			double w2 = window.get(2).getValue();
			int cmp1 = Integer.signum(Double.compare(w0, w1));
			int cmp2 = Integer.signum(Double.compare(w1, w2));
			return cmp1 == cmp2;
		});

		if(sameTrend) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
