package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.validate.DeviceInfo;

import java.util.Optional;

public interface FindDeviceByApiKeyPort {
	Optional<DeviceInfo> findDeviceByApiKey(String apiKey);
}
