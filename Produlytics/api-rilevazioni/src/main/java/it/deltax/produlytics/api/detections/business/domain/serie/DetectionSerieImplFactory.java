package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlCharts;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;


// Implementazione canonica di `DetectionSerieFactory` che costruisce una `DetectionSerieImpl`
public class DetectionSerieImplFactory implements DetectionSerieFactory {
	private final SeriePortFacade seriePortFacade;
	private final ControlCharts controlCharts;

	public DetectionSerieImplFactory(SeriePortFacade seriePortFacade, ControlCharts controlCharts) {
		this.seriePortFacade = seriePortFacade;
		this.controlCharts = controlCharts;
	}
	@Override
	public DetectionSerie createSerie(CharacteristicId characteristicId) {
		return new DetectionSerieImpl(this.seriePortFacade, this.controlCharts, characteristicId);
	}
}
