package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.List;

public class ControlChart7SameOrder implements ControlChart {
	@Override
	public void analyzeDetection(List<MarkableDetection> lastDetections, Limits limits) {
		if(lastDetections.size() < 7) {
			return;
		}

		List<MarkableDetection> last7Detections = lastDetections.subList(
			lastDetections.size() - 7,
			lastDetections.size()
		);

		int prevCmpResult = 0;
		for(int i = 0; i < 6; i++) {
			double prev = last7Detections.get(i).getValue();
			double next = last7Detections.get(i + 1).getValue();
			int cmpResult = Double.compare(prev, next);
			if(prevCmpResult == -cmpResult) {
				return;
			}
			prevCmpResult = cmpResult;
		}

		last7Detections.forEach(MarkableDetection::markOutlier);
	}
}
