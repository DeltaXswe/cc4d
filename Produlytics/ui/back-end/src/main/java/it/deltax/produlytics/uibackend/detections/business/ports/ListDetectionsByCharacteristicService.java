package it.deltax.produlytics.uibackend.detections.business.ports;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.detections.business.ports.in.ListDetectionsByCharacteristicUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ListDetectionsByCharacteristicService implements ListDetectionsByCharacteristicUseCase {

    FindAllDetectionsPort port;

    public ListDetectionsByCharacteristicService(FindAllDetectionsPort port) {
        this.port = port;
    }

    @Override
    public List<DetectionLight> listByCharacteristic(int deviceId, int characteristicId, Instant createdAfter) {
        return port.findAllByCharacteristic(deviceId, characteristicId, createdAfter);
    }
}
