package it.deltax.produlytics.api.detections.business.domain.validate;

// TODO: Questo va cambiato per ritornare deviceId e altre cose. O eliminato e sostituito da più query
public record ValidationInfo(
	String apiKey, boolean deviceArchived, boolean deviceDeactivated, boolean characteristicArchived
)
{}
