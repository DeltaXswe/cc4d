package it.deltax.produlytics.uibackend.detections.business.ports.in;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionsGroup;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

public interface GetDetectionsUseCase {
	DetectionsGroup listByCharacteristic(int deviceId, int characteristicId, DetectionFilters filters)
	throws BusinessException;
}
