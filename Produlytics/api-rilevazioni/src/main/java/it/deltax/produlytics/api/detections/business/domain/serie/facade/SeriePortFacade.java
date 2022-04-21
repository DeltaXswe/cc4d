package it.deltax.produlytics.api.detections.business.domain.serie.facade;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;

import java.util.List;

/**
 * Questa interfaccia raccoglie insieme l'interfaccia di alcune porte che sono usate sempre insieme.
 */
public interface SeriePortFacade {
	/**
	 * Questo metodo è equivalente all'omonimo definito in `InsertDetectionPort`.
	 * @param detection La rilevazione da persistere.
	 */
	void insertDetection(Detection detection);

	/**
	 * Questo metodo è equivalente all'omonimo definito in `FindLastDetectionsPort`.
	 *
	 * @param characteristicId L'identificativo globale della caratteristica di cui cercare le
	 *     rilevazioni, la quale deve esistere.
	 * @param count Il numero massimo di rilevazioni da ottenere.
	 * @return Una lista delle ultime `count` rilevazioni della caratteristica con identificativo
	 *     `characteristicId, * o meno se non ce ne sono abbastanza.
	 */
	List<Detection> findLastDetections(CharacteristicId characteristicId, int count);

	/**
	 * Questo metodo è equivalente all'omonimo definito in `MarkOutlierPort`.
	 *
	 * @param detection La rilevazione da marcare come anomala.
	 */
	void markOutlier(Detection detection);
}
