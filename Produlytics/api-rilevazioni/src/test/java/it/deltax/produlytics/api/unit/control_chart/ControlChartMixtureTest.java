package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChartMixture;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlLimits;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartMixtureTest {
	@Test
	void testRequiredDetections() {
		ControlChart controlChart = new ControlChartMixture();
		assert controlChart.requiredDetectionCount() == 8;
	}

	@Test
	void testAllUnderLowerCLimit() {
		double[] values = { 11, 12, 13, 14, 15, 16, 17, 18 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 18 < limits.lowerBCLimit();
		ControlChart controlChart = new ControlChartMixture();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllOverUpperCLimit() {
		double[] values = { 41, 42, 43, 44, 45, 46, 47, 48 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 41 > limits.upperBCLimit();
		ControlChart controlChart = new ControlChartMixture();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeOverSomeUnderCLimit() {
		double[] values = { 11, 42, 13, 44, 15, 46, 17, 48 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 17 < limits.lowerBCLimit();
		assert 42 > limits.upperBCLimit();
		ControlChart controlChart = new ControlChartMixture();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testOneInside() {
		double[] values = { 11, 12, 13, 30, 15, 16, 17, 18 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 30 > limits.lowerBCLimit();
		assert 30 < limits.upperBCLimit();
		assert 18 < limits.lowerBCLimit();
		ControlChart controlChart = new ControlChartMixture();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}
}
