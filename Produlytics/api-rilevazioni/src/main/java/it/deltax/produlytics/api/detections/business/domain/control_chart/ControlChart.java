package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.List;

public interface ControlChart {
	void analyzeDetection(List<Detection> lastDetections, MarkOutlierPort markOutlierPort, Limits limits);
}
