package it.deltax.produlytics.api.detections.business.domain.cache;

// Factory per creare una nuova istanza di `DetectionSerie`
// dati gli id di una macchina e una caratteristica.
public interface DetectionSerieFactory {
	DetectionSerie createSerie(int deviceId, int characteristicId);
}
