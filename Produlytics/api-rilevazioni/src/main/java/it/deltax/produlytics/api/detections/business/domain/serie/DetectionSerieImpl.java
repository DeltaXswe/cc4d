package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlCharts;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.control_chart.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

import java.util.List;

// Implementazione canonica di `DetectionSerie`.
public class DetectionSerieImpl implements DetectionSerie {
	private final SeriePortFacade seriePortFacade;
	private final ControlCharts controlCharts;
	private final CharacteristicId characteristicId;

	DetectionSerieImpl(
		SeriePortFacade seriePortFacade, ControlCharts controlCharts, CharacteristicId characteristicId
	) {
		this.seriePortFacade = seriePortFacade;
		this.controlCharts = controlCharts;
		this.characteristicId = characteristicId;
	}

	@Override
	public void insertDetection(Detection detection) {
		this.seriePortFacade.insertDetection(detection);

		ControlLimits controlLimits = this.computeControlLimits();
		List<? extends MarkableDetection> lastDetections = this.detectionsForControlCharts();
		this.controlCharts.analyzeDetections(lastDetections, controlLimits);
	}

	// Ritorna una lista di rilevazioni marcabili per le carte di controllo.
	private List<? extends MarkableDetection> detectionsForControlCharts() {
		// Limita il numero di rilevazioni al numero massimo accettato dalle carte di controllo,
		// o 0 se non ci sono carte di controllo.
		int count = this.controlCharts.requiredDetectionCount();
		return this.seriePortFacade.findLastDetections(this.characteristicId, count)
			.stream()
			.map(detection -> new MarkableDetectionAdapter(this.seriePortFacade, detection))
			.toList();
	}

	// Calcola i limiti di controllo utilizzando i limiti tecnici e di
	private ControlLimits computeControlLimits() {
		LimitsInfo limitsInfo = this.seriePortFacade.findLimits(this.characteristicId);
		// Prima controlla i limiti di processo.
		if(limitsInfo.meanStddev().isPresent()) {
			MeanStddev meanStddev = limitsInfo.meanStddev().get();
			double lowerLimit = meanStddev.mean() - 3 * meanStddev.stddev();
			double upperLimit = meanStddev.mean() + 3 * meanStddev.stddev();
			return new ControlLimits(lowerLimit, upperLimit);
		} else if(limitsInfo.technicalLimits().isPresent()) { // Se no controlla i limiti tecnici
			TechnicalLimits technicalLimits = limitsInfo.technicalLimits().get();
			return new ControlLimits(technicalLimits.lowerLimit(), technicalLimits.upperLimit());
		} else {
			throw new IllegalStateException(
				"Non sono impostati nè i limiti tecnici nè l'auto-adjust per la caratteristica"
					+ this.characteristicId);
		}
	}
}
