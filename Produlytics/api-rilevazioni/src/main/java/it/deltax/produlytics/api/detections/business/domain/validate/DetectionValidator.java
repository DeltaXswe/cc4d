package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.exceptions.BusinessException;

// Valida una rilevazione data la chiave API e l'id della caratteristica con cui
// è stata effettuata.
// In caso di successo ritorna delle informazioni sulla rilevazione.
// In caso di errore lancia una relativa eccezione.
public interface DetectionValidator {
	CharacteristicId validateAndFindId(String apiKey, String characteristicName) throws BusinessException;
}
