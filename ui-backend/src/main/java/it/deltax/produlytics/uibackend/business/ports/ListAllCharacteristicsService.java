package it.deltax.produlytics.uibackend.business.ports;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListAllCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListAllCharacteristicsPort;

import java.util.List;

public class ListAllCharacteristicsService implements ListAllCharacteristicsUseCase {

    private final ListAllCharacteristicsPort port;

    public ListAllCharacteristicsService(ListAllCharacteristicsPort port) {
        this.port = port;
    }

    @Override
    public List<CharacteristicLight> listAllCharacteristics() {
        return port.listAllCharacteristics();
    }
}

