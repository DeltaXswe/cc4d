package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.serie.LimitsInfo;

public interface FindLimitsPort {
	LimitsInfo findLimits(int deviceId, int characteristicId);
}
