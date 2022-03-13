package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.control_chart.CalculatedLimits;

import java.util.List;

// Implementazione di riferimento di `LimitsCalculator`.
// Viene utilizzato l'algoritmo di Welford:
// https://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.302.7503
public class LimitsCalculatorImpl implements LimitsCalculator {
	private double mean = 0;
	// s = sum_{i=1}^n (x_i - mean)^2, dove x_i sono tutti i valori considerati.
	// Equivale a `S_n` presente nell'articolo citato.
	private double s = 0;
	private int count = 0;

	@Override
	public void add(double newValue) {
		this.count++;

		double factor = ((double) this.count - 1) / this.count;

		// Equazione I dell'articolo citato.
		double meanDiff = newValue - this.mean;
		this.s += factor * meanDiff * meanDiff;

		// Identità 1 dell'articolo citato.
		this.mean = factor * this.mean + newValue / this.count;
	}

	// Effettua l'operazione inversa di `add`.
	private void remove(double oldValue) {
		// Se count == 1 allora l'algoritmo produrrebbe mean = mean / 0 - oldValue / 0 = NaN
		// e questo rompe tutti gli altri algoritmi. Lo stato interno viene quindi resettato a mano.
		if(this.count == 1) {
			this.mean = 0;
			this.s = 0;
			this.count = 0;
			return;
		}

		double factor = ((double) this.count - 1) / this.count;

		// Inverso dell'identità 1 dell'articolo citato.
		this.mean = this.mean / factor - oldValue / (this.count - 1);

		// Inverso dell'equazione I dell'articolo citato.
		double meanDiff = oldValue - this.mean;
		this.s -= factor * meanDiff * meanDiff;

		this.count--;
	}

	@Override
	public void slide(double oldValue, double newValue) {
		this.remove(oldValue);
		this.add(newValue);
	}

	@Override
	public CalculatedLimits getCalculatedLimits() {
		// σ² = (sum_{i=1}^n (x_i - mean)^2) / n = s / n
		// deviazione standard = σ = sqrt(s / n)
		return new CalculatedLimits(this.mean, Math.sqrt(this.s / this.count));
	}

	@Override
	public void reset(List<Double> values) {
		this.mean = 0;
		this.s = 0;
		this.count = 0;
		values.forEach(this::add);
	}
}
