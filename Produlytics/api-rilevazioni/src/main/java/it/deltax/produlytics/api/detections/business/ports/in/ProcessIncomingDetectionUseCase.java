package it.deltax.produlytics.api.detections.business.ports.in;

import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.exception.ArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotFoundException;

// Caso d'uso per processare una rilevazione in arrivo da una macchina.
// In caso di successo ritorna normalmente.
// In caso di errore lancia una relativa eccezione.
public interface ProcessIncomingDetectionUseCase {
	void processIncomingDetection(IncomingDetection incomingDetection)
	throws ArchivedException, NotFoundException, DeviceArchivedException, NotAuthenticatedException;
}
