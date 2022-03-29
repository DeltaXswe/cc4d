package it.deltax.produlytics.api.detections.web;

import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detections")
@SuppressWarnings("unused")
public class DetectionsController {
	@Autowired
	private ProcessIncomingDetectionUseCase processIncomingDetectionUseCase;

	// TODO: Gestisci il codice HTTP e gli errori
	@PostMapping("")
	public void addDetection(@RequestBody IncomingDetection incomingDetection) throws BusinessException {
		processIncomingDetectionUseCase.processIncomingDetection(incomingDetection);
	}
}
