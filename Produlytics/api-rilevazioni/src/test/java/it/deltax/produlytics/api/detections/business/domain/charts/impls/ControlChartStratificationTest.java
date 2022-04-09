package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetectionMock;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
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
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 25 < limits.mean();
		assert 25 > limits.lowerBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testAllInUpperC() {
		double[] values = { 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 35 > limits.mean();
		assert 35 < limits.upperBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testSomeLowerSomeUpperC() {
		double[] values = { 25, 35, 25, 35, 25, 35, 25, 35, 25, 35, 25, 35, 25, 35, 25 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 25 < limits.mean();
		assert 25 > limits.lowerBCLimit();
		assert 35 > limits.mean();
		assert 35 < limits.upperBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testSomeUnderLowerC() {
		double[] values = { 25, 25, 25, 25, 10, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 10 < limits.lowerBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testSomeOverUpperC() {
		double[] values = { 35, 35, 35, 35, 50, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 50 > limits.upperBCLimit();
		ControlChart controlChart = new ControlChartStratification();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
	}
}
