package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.charts.group.ControlCharts;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

import java.util.List;

// Implementazione canonica di `DetectionSerie`.
public class DetectionSerieImpl implements DetectionSerie {
	private final SeriePortFacade ports;
	private final ControlLimitsCalculator controlLimitsCalculator;
	private final ControlCharts controlCharts;
	private final CharacteristicId characteristicId;

	DetectionSerieImpl(
		SeriePortFacade ports,
		ControlLimitsCalculator controlLimitsCalculator,
		ControlCharts controlCharts,
		CharacteristicId characteristicId
	) {
		this.ports = ports;
		this.controlLimitsCalculator = controlLimitsCalculator;
		this.controlCharts = controlCharts;
		this.characteristicId = characteristicId;
	}

	@Override
	public void insertDetection(Detection detection) {
		this.ports.insertDetection(detection);

		ControlLimits controlLimits = this.controlLimitsCalculator.calculateControlLimits(this.characteristicId);
		List<? extends MarkableDetection> lastDetections = this.detectionsForControlCharts();
		this.controlCharts.analyzeDetections(lastDetections, controlLimits);
	}

	// Ritorna una lista di rilevazioni marcabili per le carte di controllo.
	private List<? extends MarkableDetection> detectionsForControlCharts() {
		// Limita il numero di rilevazioni al numero massimo accettato dalle carte di controllo,
		// o 0 se non ci sono carte di controllo.
		int count = this.controlCharts.requiredDetectionCount();
		return this.ports.findLastDetections(this.characteristicId, count)
			.stream()
			.map(detection -> new MarkableDetectionAdapter(this.ports, detection))
			.toList();
	}
}
