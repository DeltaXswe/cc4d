package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.DetectionInfo;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;

// Valida una rilevazione data la chiave API e l'id della caratteristica con cui
// è stata effettuata.
// In caso di successo ritorna delle informazioni sulla rilevazione.
// In caso di errore lancia una relativa eccezione.
public interface DetectionValidator {
	DetectionInfo validateAndFindDeviceId(String apiKey, int characteristicId) throws
		NotAuthenticatedException,
		CharacteristicNotFoundException,
		CharacteristicArchivedException,
		DeviceArchivedException;
}
