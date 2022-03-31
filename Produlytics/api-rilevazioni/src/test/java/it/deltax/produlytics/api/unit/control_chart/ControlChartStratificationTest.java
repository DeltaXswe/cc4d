package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChartStratification;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlLimits;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartStratificationTest {
	@Test
	void testRequiredDetections() {
		ControlChart controlChart = new ControlChartStratification();
		assert controlChart.requiredDetectionCount() == 15;
	}

	@Test
	void testAllInlowerC() {
		double[] values = { 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 25 < limits.mean();
		assert 25 > limits.lowerBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllInUpperC() {
		double[] values = { 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 35 > limits.mean();
		assert 35 < limits.upperBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeLowerSomeUpperC() {
		double[] values = { 25, 35, 25, 35, 25, 35, 25, 35, 25, 35, 25, 35, 25, 35, 25 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 25 < limits.mean();
		assert 25 > limits.lowerBCLimit();
		assert 35 > limits.mean();
		assert 35 < limits.upperBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeUnderLowerC() {
		double[] values = { 25, 25, 25, 25, 10, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 10 <  limits.lowerBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeOverUpperC() {
		double[] values = { 35, 35, 35, 35, 50, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 50 >  limits.upperBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}
}
