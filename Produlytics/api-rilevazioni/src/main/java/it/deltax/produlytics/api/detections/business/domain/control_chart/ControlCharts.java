package it.deltax.produlytics.api.detections.business.domain.control_chart;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.List;

public class ControlCharts implements ControlChart {
	private final List<ControlChart> controlCharts;

	public ControlCharts(List<ControlChart> controlCharts) {
		this.controlCharts = controlCharts;
	}

	@Override
	public void analyzeDetection(List<Detection> lastDetections, MarkOutlierPort markOutlierPort) {
		for(ControlChart controlChart : controlCharts) {
			controlChart.analyzeDetection(lastDetections, markOutlierPort);
		}
	}
}
