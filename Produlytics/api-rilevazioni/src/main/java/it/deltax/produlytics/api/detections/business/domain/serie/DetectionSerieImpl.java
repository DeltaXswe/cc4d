package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.control_chart.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

import java.util.List;

// Implementazione canonica di `DetectionSerie`.
public class DetectionSerieImpl implements DetectionSerie {
	private final SeriePortFacade seriePortFacade;

	private final List<? extends ControlChart> controlCharts;

	private final CharacteristicId characteristicId;

	DetectionSerieImpl(
		SeriePortFacade seriePortFacade, List<? extends ControlChart> controlCharts, CharacteristicId characteristicId
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
		this.interrogateControlCharts(controlLimits, lastDetections);
	}

	// Interroga le liste di controllo, fornendo i limiti e le rilevazioni presi in input.
	private void interrogateControlCharts(
		ControlLimits controlLimits, List<? extends MarkableDetection> lastDetections
	) {
		for(ControlChart controlChart : this.controlCharts) {
			// Evita d'interrogare la carta di controllo se non ci sono abbastanza rilevazioni.
			int count = controlChart.requiredDetectionCount();
			if(lastDetections.size() >= count) {
				// Passa solo le ultime rilevazioni richieste dalla carta di controllo.
				controlChart.analyzeDetections(this.cutLastDetections(lastDetections, count), controlLimits);
			}
		}
	}

	// Ritorna una lista di rilevazioni marcabili per le carte di controllo.
	private List<? extends MarkableDetection> detectionsForControlCharts() {
		// Limita il numero di rilevazioni al numero massimo accettato dalle carte di controllo,
		// o 0 se non ci sono carte di controllo.
		int maxCount = this.controlCharts.stream().mapToInt(ControlChart::requiredDetectionCount).max().orElse(0);
		return this.seriePortFacade.findLastDetections(this.characteristicId, maxCount)
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

	// Ritorna una lista contenente solo le ultime `count` rilevazioni di `lastDetections`
	private List<? extends MarkableDetection> cutLastDetections(
		List<? extends MarkableDetection> lastDetections, int count
	) {
		return lastDetections.subList(lastDetections.size() - count, lastDetections.size());
	}
}
