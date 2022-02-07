package it.deltax.produlytics.api.business.ports.out;

import it.deltax.produlytics.api.business.domain.Detection;
import it.deltax.produlytics.api.business.domain.DetectionLight;


public interface InsertDetectionPort {
    Detection insertDetection(DetectionLight rilevazione);
}
