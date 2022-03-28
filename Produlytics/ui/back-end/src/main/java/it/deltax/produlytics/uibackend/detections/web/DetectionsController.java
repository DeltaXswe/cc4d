package it.deltax.produlytics.uibackend.detections.web;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.detections.business.ports.in.ListDetectionsByCharacteristicUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
// smth wrong here
@RequestMapping("/detections")
public class DetectionsController {

    private final ListDetectionsByCharacteristicUseCase listDetectionsByCharacteristic;

    public DetectionsController(@Qualifier("getListDetectionsByCharacteristicUseCase") ListDetectionsByCharacteristicUseCase listDetectionsByCharacteristic) {
        this.listDetectionsByCharacteristic = listDetectionsByCharacteristic;
    }

    @GetMapping("/{device}/characteristics/{characteristic}")
    public List<DetectionLight> getCharacteristicDetections(
        @PathVariable int device,
        @PathVariable int characteristic,
        @RequestParam("createdAfter") Long createdAfter
    ) {
        return listDetectionsByCharacteristic.listByCharacteristic(device, characteristic, createdAfter);
    }
}
