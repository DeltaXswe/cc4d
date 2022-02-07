package it.deltax.produlytics.api.web;

import it.deltax.produlytics.api.business.domain.Detection;
import it.deltax.produlytics.api.business.domain.DetectionLight;
import it.deltax.produlytics.api.business.ports.in.InsertDetectionUseCase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detections")
public class DetectionsController {

    @Autowired
    private InsertDetectionUseCase useCase;

    @PostMapping("")
    public Optional<Detection> addRilevazione(@RequestBody DetectionLight detectionLight) {
		  return useCase.insertRilevazione(detectionLight);
    }
}
