package it.deltax.produlytics.uibackend.common.detection.web;

import it.deltax.produlytics.uibackend.common.detection.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.common.detection.business.ports.in.ListDetectionsByCharacteristicUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
// smth wrong here
@RequestMapping("/detections")
public class DetectionsController {

    private final ListDetectionsByCharacteristicUseCase listDetectionsByCharacteristic;

    public DetectionsController(ListDetectionsByCharacteristicUseCase listDetectionsByCharacteristic) {
        this.listDetectionsByCharacteristic = listDetectionsByCharacteristic;
    }

    @GetMapping("/{machine}/characteristics/{characteristic}")
    public List<DetectionLight> getCharacteristicDetections(
        @PathVariable int machine,
        @PathVariable int characteristic,
        @RequestParam("createdAfter") Optional<Long> createdAfter
    ) {
        return listDetectionsByCharacteristic.listByCharacteristic(machine, characteristic, createdAfter);
    }
}
