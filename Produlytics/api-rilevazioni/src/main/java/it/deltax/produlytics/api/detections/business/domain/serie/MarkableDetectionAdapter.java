package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;

/**
 * Questa classe implementa una rilevazione che può essere marcata come anomala.
 */
public class MarkableDetectionAdapter implements MarkableDetection {
	/**
	 * La porta utilizzata per marcare le rilevazioni come anomale.
	 */
	private final SeriePortFacade seriePortFacade;
	/**
	 * La rilevazione.
	 */
	private final Detection detection;

	/**
	 * Crea una nuova istanza di `MarkableDetectionAdapter`.
	 *
	 * @param seriePortFacade Il valore per il campo `seriePortFacade`.
	 * @param detection Il valore per il campo `detection`.
	 */
	public MarkableDetectionAdapter(SeriePortFacade seriePortFacade, Detection detection) {
		this.seriePortFacade = seriePortFacade;
		this.detection = detection;
	}

	/**
	 * Questo metodo implementa l'omonimo definito in `MarkableDetection`.
	 *
	 * @return Il valore della rilevazione;
	 */
	@Override
	public double value() {
		return this.detection.value();
	}

	/**
	 * Questo metodo implementa l'omonimo definito in `MarkableDetection`.
	 */
	@Override
	public void markOutlier() {
		this.seriePortFacade.markOutlier(this.detection);
	}
}
