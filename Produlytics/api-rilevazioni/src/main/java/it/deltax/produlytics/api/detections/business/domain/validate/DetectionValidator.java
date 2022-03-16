package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.ValidationInfo;
import it.deltax.produlytics.api.detections.business.domain.exception.ArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotFoundException;

// Valida una rilevazione data la chiave API e l'id della caratteristica con cui
// è stata effettuata.
// In caso di successo ritorna delle informazioni sulla rilevazione.
// In caso di errore lancia una relativa eccezione.
public interface DetectionValidator {
	ValidationInfo validateAndFindDeviceId(String apiKey, int characteristicId)
	throws NotAuthenticatedException, NotFoundException, ArchivedException;
}
