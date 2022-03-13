package it.deltax.produlytics.api.detections.business.ports.in;

import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;

// Caso d'uso per processare una rilevazione in arrivo da una macchina.
// In caso di successo ritorna normalmente.
// In caso di errore lancia una relativa eccezione.
public interface ProcessIncomingDetectionUseCase {
	void processIncomingDetection(IncomingDetection incomingDetection) throws
		CharacteristicArchivedException,
		CharacteristicNotFoundException,
		DeviceArchivedException,
		NotAuthenticatedException;
}
