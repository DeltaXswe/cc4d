package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.control_chart.MarkableDetection;

import java.util.Arrays;
import java.util.List;

public class MarkableDetectionHelper implements MarkableDetection {
	static List<MarkableDetectionHelper> listFromValues(double... values) {
		return Arrays.stream(values).mapToObj(value -> new MarkableDetectionHelper(value, false)).toList();
	}

	private final double value;
	private boolean outlier;

	public MarkableDetectionHelper(double value, boolean outlier) {
		this.value = value;
		this.outlier = outlier;
	}

	@Override
	public void markOutlier() {
		this.outlier = true;
	}

	@Override
	public double value() {
		return this.value;
	}

	public boolean isOutlier() {
		return this.outlier;
	}
}
