package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.List;

public interface ControlChart {
	void analyzeDetection(List<MarkableDetection> lastDetections, Limits limits);
}
