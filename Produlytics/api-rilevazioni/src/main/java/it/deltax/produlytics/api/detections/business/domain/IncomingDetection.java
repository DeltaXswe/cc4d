package it.deltax.produlytics.api.detections.business.domain;

import java.time.Instant;

public record IncomingDetection(String apiKey, int characteristicId, double value) {
	public RawDetection toRawDetection(int deviceId) {
		return new RawDetection(deviceId, this.characteristicId(), Instant.now(), this.value());
	}
}
