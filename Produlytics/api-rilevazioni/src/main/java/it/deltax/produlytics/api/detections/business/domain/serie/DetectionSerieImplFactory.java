package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartsGroup;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

/**
 * Questa classe si occupa di creare una `DetectionSerieImpl`.
 */
public class DetectionSerieImplFactory implements DetectionSerieFactory {
	/**
	 * Le porte utilizzate da `DetectionSerieImpl`.
	 */
	private final SeriePortFacade ports;
	/**
	 * Un calcolatore di limiti di controllo utilizzato dalla `DetectionSerieImpl`.
	 */
	private final ControlLimitsCalculator controlLimitsCalculator;
	/**
	 * Le carte di controllo che la `DetectionSerieImpl` applicherà.
	 */
	private final ControlChartsGroup controlChartsGroup;

	/**
	 * Crea una nuova istanza di `DetectionSerieImplFactory`.
	 *
	 * @param ports Il valore per il campo `ports`.
	 * @param controlLimitsCalculator Il valore per il campo `controlLimitsCalculator`.
	 * @param controlChartsGroup Il valore per il campo `controlChartsGroup`.
	 */
	public DetectionSerieImplFactory(
		SeriePortFacade ports, ControlLimitsCalculator controlLimitsCalculator, ControlChartsGroup controlChartsGroup
	) {
		this.ports = ports;
		this.controlLimitsCalculator = controlLimitsCalculator;
		this.controlChartsGroup = controlChartsGroup;
	}

	/**
	 *  Questo metodo implementa l'omonimo metodo definito in `DetectionSerieFactory`, creando una `DetectionSerieImpl`.
	 *
	 * @param characteristicId L'identificativo globale della caratteristica a cui sarà associata la `DetectionSerie`.
	 * @return Una nuova istanza di `DetectionSerieImpl`.
	 */
	@Override
	public DetectionSerie createSerie(CharacteristicId characteristicId) {
		return new DetectionSerieImpl(this.ports, this.controlLimitsCalculator, this.controlChartsGroup, characteristicId);
	}
}
