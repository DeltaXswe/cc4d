package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.List;

// Rappresenta l'implementazione di una carta di controllo.
public interface ControlChart {
	int requiredDetectionCount();

	// `lastDetections` sono le ultime rilevazioni (massimo 15, ma potrebbero essere di meno).
	// `limits` sono i valori limite, calcolati o forniti dall'amministratore, della caratteristica in considerazione.
	void analyzeDetections(List<MarkableDetection> lastDetections, ControlLimits limits);
}
