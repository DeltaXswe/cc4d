package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;

import java.util.List;

// Ottiene le ultime `count` rilevazioni di una caratteristica, ordinate per data di creazione,
// dati l'id della macchina a cui appartiene e il suo id all'interno di essa.
public interface FindLastDetectionsPort {
	List<Detection> findLastDetections(CharacteristicId characteristicId, int count);
}
