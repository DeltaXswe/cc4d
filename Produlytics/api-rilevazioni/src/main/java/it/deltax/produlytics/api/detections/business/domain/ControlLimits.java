package it.deltax.produlytics.api.detections.business.domain;

public record ControlLimits(double lowerLimit, double upperLimit) {
	public double mean() {
		return (this.lowerLimit() + this.upperLimit()) / 2;
	}

	private double stddev() {
		return (this.upperLimit() - this.lowerLimit()) / 6;
	}

	public double lowerBCLimit() {
		return this.mean() - this.stddev();
	}

	public double upperBCLimit() {
		return this.mean() + this.stddev();
	}

	public double lowerABLimit() {
		return this.mean() - 2 * this.stddev();
	}

	public double upperABLimit() {
		return this.mean() + 2 * this.stddev();
	}
}
