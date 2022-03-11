package it.deltax.produlytics.api.detections.business.domain.cache;

public interface DetectionSerieFactory {
	DetectionSerie createSerie(int deviceId, int characteristicId);
}
