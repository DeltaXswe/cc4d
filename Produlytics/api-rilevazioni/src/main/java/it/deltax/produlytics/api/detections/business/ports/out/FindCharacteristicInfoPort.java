package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicInfo;

import java.util.Optional;

// Ottiene le informazioni di una caratteristica dato l'id della macchina
// a cui appartiene e il suo id all'interno di essa.
// Ritorna Optional.empty() se la caratteristica cercata non esiste.
public interface FindCharacteristicInfoPort {
	Optional<CharacteristicInfo> findCharacteristic(CharacteristicId characteristicId);
}
