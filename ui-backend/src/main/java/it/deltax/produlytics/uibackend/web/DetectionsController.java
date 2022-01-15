package it.deltax.produlytics.uibackend.web;

import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListDetectionsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detections")
public class DetectionsController {

    private final ListDetectionsUseCase listDetectionsUseCase;

    public DetectionsController(ListDetectionsUseCase listDetectionsUseCase) {
        this.listDetectionsUseCase = listDetectionsUseCase;
    }

    @GetMapping("/{machine}/{characteristic}")
    public List<DetectionLight> getCharacteristicDetections(
            @PathVariable long machine,
            @PathVariable String characteristic,
            @RequestParam("createdAfter") Optional<Long> createdAfter
    ) {
        return listDetectionsUseCase.listDetections(machine, characteristic, createdAfter);
    }
}
