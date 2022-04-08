package it.deltax.produlytics.api.detections.web;

import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detections")
@SuppressWarnings("unused")
public class DetectionsController {
	@Autowired
	private ProcessIncomingDetectionUseCase processIncomingDetectionUseCase;

	@PostMapping("")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void addDetection(@RequestBody IncomingDetection incomingDetection) throws BusinessException {
		processIncomingDetectionUseCase.processIncomingDetection(incomingDetection);
	}
}
