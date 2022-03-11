package it.deltax.produlytics.api.detections.business.domain.queue;

public interface DetectionSerieFactory {
	DetectionSerie createSerie(int deviceId, int characteristicId);
}
