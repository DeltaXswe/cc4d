package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;

// Factory per creare una nuova istanza di `DetectionSerie`
// dati gli id di una macchina e una caratteristica.
public interface DetectionSerieFactory {
	DetectionSerie createSerie(CharacteristicId characteristicId);
}
