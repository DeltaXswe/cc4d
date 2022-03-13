package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.control_chart.CalculatedLimits;

import java.util.List;

// Rappresenta una classe che calcola media e deviazione standard
// di una serie di valori in modo incrementale.
public interface LimitsCalculator {
	// Aggiunge un nuovo valore alla serie esistente.
	void add(double newValue);
	// Rimuove un vecchio valore e ne aggiunge uno nuovo alla serie esistente.
	void slide(double oldValue, double newValue);
	// Reimposta la serie con nuovi valori.
	void reset(List<Double> values);
	// Ritorna la media e la deviazione standard attualmente calcolati.
	CalculatedLimits getCalculatedLimits();
}
