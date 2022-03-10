package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.validate.DeviceValidationInfo;

import java.util.Optional;

public interface FindDeviceValidationByApiKeyPort {
	Optional<DeviceValidationInfo> findDeviceByApiKey(String apiKey);
}
