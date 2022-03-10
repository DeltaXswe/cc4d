package it.deltax.produlytics.api.detections.business.domain.validate;

public record ValidationInfo(String apiKey,
	boolean deviceArchived,
	boolean deviceDeactivated,
	boolean characteristicArchived)
{}
