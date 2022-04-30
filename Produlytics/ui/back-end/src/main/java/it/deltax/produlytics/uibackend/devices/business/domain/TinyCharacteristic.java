package it.deltax.produlytics.uibackend.devices.business.domain;

/**
 * Record che rappresenta l'intestazione di una caratteristica, con l'identificativo e il nome.
 */
public record TinyCharacteristic(
  int id,
  String name
) {}
