package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.charts.group.ControlCharts;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

// Implementazione canonica di `DetectionSerieFactory` che costruisce una `DetectionSerieImpl`
public class DetectionSerieImplFactory implements DetectionSerieFactory {
	private final SeriePortFacade ports;
	private final ControlLimitsCalculator controlLimitsCalculator;
	private final ControlCharts controlCharts;

	public DetectionSerieImplFactory(
		SeriePortFacade ports, ControlLimitsCalculator controlLimitsCalculator, ControlCharts controlCharts
	) {
		this.ports = ports;
		this.controlLimitsCalculator = controlLimitsCalculator;
		this.controlCharts = controlCharts;
	}
	@Override
	public DetectionSerie createSerie(CharacteristicId characteristicId) {
		return new DetectionSerieImpl(this.ports, this.controlLimitsCalculator, this.controlCharts, characteristicId);
	}
}
