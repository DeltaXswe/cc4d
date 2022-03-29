package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.serie.LimitsInfo;

// Ottiene i limiti tecnici e/o di processo di una caratteristica, se esistono, altrimenti ritorna `Optional.empty()`.
public interface FindLimitsPort {
	LimitsInfo findLimits(int deviceId, int characteristicId);
}
