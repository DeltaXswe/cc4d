package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

/** Record che rappresenta una macchina con tutti i suoi dati. */
public record DetailedDevice(
    int id,
    String name,
    boolean archived,
    boolean deactivated,
    String apiKey
) {
  @Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
  public DetailedDevice {}
}