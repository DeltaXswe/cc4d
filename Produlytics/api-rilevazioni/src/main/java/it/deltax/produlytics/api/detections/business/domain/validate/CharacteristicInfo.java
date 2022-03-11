package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.LimitsInfo;

public record CharacteristicInfo(
	boolean archived, LimitsInfo limitsInfo
) {}
