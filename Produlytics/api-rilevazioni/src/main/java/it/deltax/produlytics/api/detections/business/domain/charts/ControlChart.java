package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

// Rappresenta l'implementazione di una carta di controllo.
public interface ControlChart {
	// Ritorna il numero di rilevazioni che devono essere presenti nella lista passata come argomento
	// a `analyzeDetections`. Tale numero deve essere sempre lo stesso per una data istanza di `ControlChart`.
	int requiredDetectionCount();

	// `lastDetections` sono le ultime requiredDetectionCount() rilevazioni
	// `limits` sono i valori limite, calcolati o forniti dall'amministratore, della caratteristica in considerazione.
	void analyzeDetections(List<? extends MarkableDetection> lastDetections, ControlLimits limits);
}
