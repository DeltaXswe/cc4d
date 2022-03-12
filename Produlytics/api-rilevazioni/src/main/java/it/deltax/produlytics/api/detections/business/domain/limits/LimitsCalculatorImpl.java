package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.CalculatedLimits;

import java.util.List;

public class LimitsCalculatorImpl implements LimitsCalculator {
	private double mean = 0;
	private double dSquared = 0;
	private int count = 0;

	@Override
	public void add(Detection newDetection) {
		double newValue = newDetection.value();
		double oldMean = this.mean;
		this.count++;
		this.mean += (newValue - oldMean) / this.count;
		this.dSquared += (newValue - this.mean) * (newValue - oldMean);
	}

	@Override
	public void slide(Detection oldDetection, Detection newDetection) {
		double oldValue = oldDetection.value();
		double newValue = newDetection.value();
		double oldMean = this.mean;
		this.mean += (newValue - oldValue) / this.count;
		this.dSquared += (newValue - oldValue) * (newValue + oldValue - this.mean - oldMean);
	}

	@Override
	public CalculatedLimits getCalculatedLimits() {
		return new CalculatedLimits(this.mean, Math.sqrt(this.dSquared / (this.count - 1)));
	}

	@Override
	public void reset(List<Detection> detections) {
		this.mean = detections.stream().mapToDouble(detection -> detection.value() / detections.size()).sum();
		this.dSquared = detections.stream()
			.mapToDouble(detection -> detection.value() - this.mean)
			.map(d -> (d * d))
			.sum();
		this.count = detections.size();
	}
}
