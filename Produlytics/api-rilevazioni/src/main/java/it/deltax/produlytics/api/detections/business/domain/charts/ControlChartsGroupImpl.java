package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

public class ControlChartsGroupImpl implements ControlChartsGroup {
	private final List<? extends ControlChart> controlCharts;

	public ControlChartsGroupImpl(List<? extends ControlChart> controlCharts) {
		this.controlCharts = controlCharts;
	}

	@Override
	public int requiredDetectionCount() {
		return this.controlCharts.stream().mapToInt(ControlChart::requiredDetectionCount).max().orElse(0);
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> detections, ControlLimits limits) {
		for(ControlChart controlChart : this.controlCharts) {
			// Evita d'interrogare la carta di controllo se non ci sono abbastanza rilevazioni.
			int count = controlChart.requiredDetectionCount();
			if (detections.size() < count) {
				continue;
			}
			// Passa solo le ultime rilevazioni richieste dalla carta di controllo.
			controlChart.analyzeDetections(this.cutLastDetections(detections, count), limits);
		}
	}

	// Ritorna una lista contenente solo le ultime `count` rilevazioni di `lastDetections`
	private List<? extends MarkableDetection> cutLastDetections(
		List<? extends MarkableDetection> lastDetections, int count
	) {
		return lastDetections.subList(lastDetections.size() - count, lastDetections.size());
	}
}
