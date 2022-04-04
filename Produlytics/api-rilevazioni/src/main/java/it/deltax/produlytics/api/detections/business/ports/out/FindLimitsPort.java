package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.limits.LimitsInfo;

// Ottiene i limiti tecnici e/o di processo di una caratteristica, se esistono, altrimenti ritorna `Optional.empty()`.
public interface FindLimitsPort {
	LimitsInfo findLimits(CharacteristicId characteristicId);
}
