package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.validate.ValidationInfo;

import java.util.Optional;

public interface FindValidationInfoPort {
	Optional<ValidationInfo> findValidationInfo(String apiKey, int characteristicId);
}
