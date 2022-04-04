package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlLimits;

public interface ControlLimitsCalculator {
	ControlLimits calculateControlLimits(CharacteristicId characteristicId);
}
