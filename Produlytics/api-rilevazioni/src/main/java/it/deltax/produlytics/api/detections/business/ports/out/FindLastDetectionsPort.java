package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.MarkableDetection;

import java.util.List;

// Ottiene le ultime `count` rilevazioni di una caratteristica dato l'id della macchina
// a cui appartiene e il suo id all'interno di essa.
public interface FindLastDetectionsPort {
	List<MarkableDetection> findLastDetections(int deviceId, int characteristicId, int count);
}
