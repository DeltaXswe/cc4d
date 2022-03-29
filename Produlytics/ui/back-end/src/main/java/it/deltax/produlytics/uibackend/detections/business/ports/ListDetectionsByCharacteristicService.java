package it.deltax.produlytics.uibackend.detections.business.ports;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.detections.business.ports.in.ListDetectionsByCharacteristicUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.ListDetectionsByCharacteristicPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListDetectionsByCharacteristicService implements ListDetectionsByCharacteristicUseCase {

    ListDetectionsByCharacteristicPort port;

    public ListDetectionsByCharacteristicService(ListDetectionsByCharacteristicPort port) {
        this.port = port;
    }

    @Override
    public List<DetectionLight> listByCharacteristic(int deviceId, int characteristicId, Long createdAfter) {
        return port.listByCharacteristic(deviceId, characteristicId, createdAfter);
    }
}
