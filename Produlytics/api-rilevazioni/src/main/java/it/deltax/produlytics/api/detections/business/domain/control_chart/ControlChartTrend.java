package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.List;

public class ControlChartTrend implements ControlChart {
	@Override
	public void analyzeDetection(List<MarkableDetection> lastDetections, Limits limits) {
		if(lastDetections.size() < 7) {
			return;
		}
		List<MarkableDetection> detections = lastDetections.subList(lastDetections.size() - 7, lastDetections.size());

		// TODO: Questo è incasinato
		int prevCmpResult = 2;
		for(int i = 0; i < 6; i++) {
			double prev = detections.get(i).getValue();
			double next = detections.get(i + 1).getValue();
			int cmpResult = Integer.signum(Double.compare(prev, next));
			if(cmpResult == 0) {
				return;
			}
			if(prevCmpResult != 2 && prevCmpResult != cmpResult) {
				return;
			}
			prevCmpResult = cmpResult;
		}

		detections.forEach(MarkableDetection::markOutlier);
	}
}
