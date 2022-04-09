package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetectionMock;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartZoneATest {
	@Test
	void testRequiredDetections() {
		ControlChart controlChart = new ControlChartZoneA();
		assert controlChart.requiredDetectionCount() == 3;
	}

	@Test
	void testAllLowerA() {
		double[] values = { 5, 5, 5 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 5 < limits.lowerABLimit();
		ControlChart controlChart = new ControlChartZoneA();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testAllUpperA() {
		double[] values = { 55, 55, 55 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 55 > limits.upperABLimit();
		ControlChart controlChart = new ControlChartZoneA();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testSomeLowerA() {
		double[] values = { 5, 5, 30 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 5 < limits.lowerABLimit();
		ControlChart controlChart = new ControlChartZoneA();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testSomeUpperA() {
		double[] values = { 55, 55, 30 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 55 > limits.upperABLimit();
		ControlChart controlChart = new ControlChartZoneA();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testAllLowerAOrUnder() {
		double[] values = { 5, -5, -5 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert -5 < limits.lowerLimit();
		ControlChart controlChart = new ControlChartZoneA();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testAllUpperAOrOver() {
		double[] values = { 55, 65, 65 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 65 > limits.upperLimit();
		ControlChart controlChart = new ControlChartZoneA();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testSomeUpperSomeLowerA() {
		double[] values = { 55, 5, 30 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 5 < limits.lowerABLimit();
		assert 55 > limits.upperABLimit();
		ControlChart controlChart = new ControlChartZoneA();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testSomeBC() {
		double[] values = { 55, 15, 45 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 15 > limits.lowerABLimit();
		assert 15 < limits.upperABLimit();
		ControlChart controlChart = new ControlChartZoneA();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
	}
}
