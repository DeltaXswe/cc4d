package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

/** Record che rappresenta una macchina con tutti i suoi dati, meno la apiKey. */
public record Device(
    int id,
    String name,
    boolean archived,
    boolean deactivated
) {
  @Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
  public Device {}
}
