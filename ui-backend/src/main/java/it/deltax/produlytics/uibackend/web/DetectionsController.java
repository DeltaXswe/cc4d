package it.deltax.produlytics.uibackend.web;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListDetectionsByCharacteristicUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detections")
public class DetectionsController {

    private final ListDetectionsByCharacteristicUseCase listDetectionsByCharacteristic;

    public DetectionsController(ListDetectionsByCharacteristicUseCase listDetectionsByCharacteristic) {
        this.listDetectionsByCharacteristic = listDetectionsByCharacteristic;
    }

    @GetMapping("/{machine}/{characteristic}")
    public List<DetectionLight> getCharacteristicDetections(
            @PathVariable long machine,
            @PathVariable String characteristic,
            @RequestParam("createdAfter") Optional<Long> createdAfter
    ) {
        return listDetectionsByCharacteristic.listByCharacteristic(machine, characteristic, createdAfter);
    }
}
