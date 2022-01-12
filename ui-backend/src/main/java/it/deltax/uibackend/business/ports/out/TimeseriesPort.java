package it.deltax.uibackend.business.ports.out;

import it.deltax.uibackend.business.domain.TimeseriesPointLight;

import java.util.List;

public interface TimeseriesPort {
    List<TimeseriesPointLight> filterByCharacteristic(long machine, String characteristicName);
}
