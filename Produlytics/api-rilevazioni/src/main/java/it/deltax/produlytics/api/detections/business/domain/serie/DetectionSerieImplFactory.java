package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

import java.util.List;

// Implementazione canonica di `DetectionSerieFactory` che costruisce una `DetectionSerieImpl`
public class DetectionSerieImplFactory implements DetectionSerieFactory {
	private final SeriePortFacade seriePortFacade;
	private final List<? extends ControlChart> controlCharts;

	public DetectionSerieImplFactory(SeriePortFacade seriePortFacade, List<? extends ControlChart> controlCharts) {
		this.seriePortFacade = seriePortFacade;
		this.controlCharts = controlCharts;
	}
	@Override
	public DetectionSerie createSerie(CharacteristicId characteristicId) {
		return new DetectionSerieImpl(this.seriePortFacade, this.controlCharts, characteristicId);
	}
}
