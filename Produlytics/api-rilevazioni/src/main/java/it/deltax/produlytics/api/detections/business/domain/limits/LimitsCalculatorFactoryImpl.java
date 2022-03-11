package it.deltax.produlytics.api.detections.business.domain.limits;

public class LimitsCalculatorFactoryImpl implements LimitsCalculatorFactory {
	@Override
	public LimitsCalculator createCalculator() {
		return new LimitsCalculatorImpl();
	}
}
