package it.deltax.produlytics.uibackend.business.ports;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListDetectionsByCharacteristicUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListDetectionsByCharacteristicPort;

import java.util.List;
import java.util.Optional;

public class ListDetectionsByCharacteristicService implements ListDetectionsByCharacteristicUseCase {

    ListDetectionsByCharacteristicPort port;

    public ListDetectionsByCharacteristicService(ListDetectionsByCharacteristicPort port) {
        this.port = port;
    }

    @Override
    public List<DetectionLight> listByCharacteristic(long machineId, String characteristicCode, Optional<Long> createdAfter) {
        return port.listByCharacteristic(machineId, characteristicCode, createdAfter);
    }
}
