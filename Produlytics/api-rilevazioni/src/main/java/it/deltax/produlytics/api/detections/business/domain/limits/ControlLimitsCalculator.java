package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;

public interface ControlLimitsCalculator {
	ControlLimits calculateControlLimits(CharacteristicId characteristicId);
}
