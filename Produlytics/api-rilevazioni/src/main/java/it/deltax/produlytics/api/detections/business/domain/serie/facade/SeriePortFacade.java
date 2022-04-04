package it.deltax.produlytics.api.detections.business.domain.serie.facade;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;

import java.util.List;

public interface SeriePortFacade {
	void insertDetection(Detection detection);

	List<Detection> findLastDetections(CharacteristicId characteristicId, int count);

	void markOutlier(Detection detection);
}
