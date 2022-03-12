package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.List;

public class ControlChart7SameOrder implements ControlChart {
	@Override
	public void analyzeDetection(List<Detection> lastDetections, MarkOutlierPort markOutlierPort, Limits limits) {
		if(lastDetections.size() < 7) {
			return;
		}

		List<Detection> last7Detections = lastDetections.subList(lastDetections.size() - 7, lastDetections.size());

		int prevCmpResult = 0;
		for(int i = 0; i < 6; i++) {
			Detection prev = last7Detections.get(i);
			Detection next = last7Detections.get(i + 1);
			int cmpResult = Double.compare(prev.value(), next.value());
			if(prevCmpResult == -cmpResult) {
				return;
			}
			prevCmpResult = cmpResult;
		}

		for(Detection detection : last7Detections) {
			markOutlierPort.markOutlier(detection);
		}
	}
}
