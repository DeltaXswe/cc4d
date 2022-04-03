package it.deltax.produlytics.api.detections.business.domain.serie.facade;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.serie.LimitsInfo;

import java.util.List;

public interface SeriePortFacade {
	void insertDetection(Detection detection);

	LimitsInfo findLimits(CharacteristicId characteristicId);

	List<Detection> findLastDetections(CharacteristicId characteristicId, int count);

	void markOutlier(Detection detection);
}
