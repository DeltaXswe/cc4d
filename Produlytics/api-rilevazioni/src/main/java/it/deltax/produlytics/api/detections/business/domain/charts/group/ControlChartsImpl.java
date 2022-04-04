package it.deltax.produlytics.api.detections.business.domain.charts.group;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;

import java.util.List;

public class ControlChartsImpl implements ControlCharts {
	private final List<? extends ControlChart> controlCharts;

	public ControlChartsImpl(List<? extends ControlChart> controlCharts) {
		this.controlCharts = controlCharts;
	}

	@Override
	public int requiredDetectionCount() {
		return this.controlCharts.stream().mapToInt(ControlChart::requiredDetectionCount).max().orElse(0);
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> lastDetections, ControlLimits limits) {
		for(ControlChart controlChart : this.controlCharts) {
			// Evita d'interrogare la carta di controllo se non ci sono abbastanza rilevazioni.
			int count = controlChart.requiredDetectionCount();
			if(lastDetections.size() >= count) {
				// Passa solo le ultime rilevazioni richieste dalla carta di controllo.
				controlChart.analyzeDetections(this.cutLastDetections(lastDetections, count), limits);
			}
		}
	}

	// Ritorna una lista contenente solo le ultime `count` rilevazioni di `lastDetections`
	private List<? extends MarkableDetection> cutLastDetections(
		List<? extends MarkableDetection> lastDetections, int count
	) {
		return lastDetections.subList(lastDetections.size() - count, lastDetections.size());
	}
}
