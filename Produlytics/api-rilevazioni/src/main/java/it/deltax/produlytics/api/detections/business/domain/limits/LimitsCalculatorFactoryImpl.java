package it.deltax.produlytics.api.detections.business.domain.limits;

// Implementazione di riferimento di `LimitsCalculatorFactory` che costruisce un nuovo `LimitsCalculatorImpl`.
public class LimitsCalculatorFactoryImpl implements LimitsCalculatorFactory {
	@Override
	public LimitsCalculator createCalculator() {
		return new LimitsCalculatorImpl();
	}
}
