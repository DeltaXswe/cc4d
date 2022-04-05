package it.deltax.produlytics.uibackend.detections.business.ports.in;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.Detections;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface GetDetectionsUseCase {
	Detections listByCharacteristic(int deviceId, int characteristicId, DetectionFilters filters)
	throws BusinessException;
}
