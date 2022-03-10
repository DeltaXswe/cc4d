package it.deltax.produlytics.api.detections.business.domain.cache;

public interface DetectionCacheFactory {
	DetectionCache createCache(int deviceId, int characteristicId);
}
