package it.deltax.produlytics.uibackend.detections.business.ports.in;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionsGroup;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * L'interfaccia che rappresenta il caso d'uso di ottenimento delle rilevazioni di una caratteristica di una macchina
 */
public interface GetDetectionsUseCase {
	DetectionsGroup listByCharacteristic(int deviceId, int characteristicId, DetectionFilters filters)
	throws BusinessException;
}
