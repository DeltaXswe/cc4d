package it.deltax.produlytics.api.repositories;

public interface ValidationInfoProjection {
	String apiKey();
	boolean deviceArchived();
	boolean deviceDeactivated();
	boolean characteristicArchived();
}
