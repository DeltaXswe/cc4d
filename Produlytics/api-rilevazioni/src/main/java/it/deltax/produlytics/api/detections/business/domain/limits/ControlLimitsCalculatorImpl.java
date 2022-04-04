package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.ports.out.FindLimitsPort;

public class ControlLimitsCalculatorImpl implements ControlLimitsCalculator {
	private final FindLimitsPort findLimitsPort;

	public ControlLimitsCalculatorImpl(FindLimitsPort findLimitsPort) {
		this.findLimitsPort = findLimitsPort;
	}

	@Override
	public ControlLimits calculateControlLimits(CharacteristicId characteristicId) {
		LimitsInfo limitsInfo = this.findLimitsPort.findLimits(characteristicId);
		// Prima controlla i limiti di processo.
		if(limitsInfo.meanStddev().isPresent()) {
			return this.fromMeanStddev(limitsInfo.meanStddev().get());
		} else if(limitsInfo.technicalLimits().isPresent()) { // Se no controlla i limiti tecnici
			return this.fromTechnicalLimits(limitsInfo.technicalLimits().get());
		} else {
			throw new IllegalStateException(
				"Non sono impostati nè i limiti tecnici nè l'auto-adjust per la caratteristica" + characteristicId);
		}
	}

	private ControlLimits fromMeanStddev(MeanStddev meanStddev) {
		double lowerLimit = meanStddev.mean() - 3 * meanStddev.stddev();
		double upperLimit = meanStddev.mean() + 3 * meanStddev.stddev();
		return new ControlLimits(lowerLimit, upperLimit);
	}

	private ControlLimits fromTechnicalLimits(TechnicalLimits technicalLimits) {
		return new ControlLimits(technicalLimits.lowerLimit(), technicalLimits.upperLimit());
	}
}
