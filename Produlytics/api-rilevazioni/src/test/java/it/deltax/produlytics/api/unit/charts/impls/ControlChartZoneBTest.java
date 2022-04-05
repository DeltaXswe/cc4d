package it.deltax.produlytics.api.unit.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartZoneB;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import it.deltax.produlytics.api.unit.charts.MarkableDetectionHelper;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartZoneBTest {
	@Test
	void testRequiredDetections() {
		ControlChart controlChart = new ControlChartZoneB();
		assert controlChart.requiredDetectionCount() == 5;
	}

	@Test
	void testAllLowerB() {
		double[] values = { 15, 15, 15, 15, 15 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 15 < limits.lowerBCLimit();
		assert 15 > limits.lowerABLimit();
		ControlChart controlChart = new ControlChartZoneB();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllUpperB() {
		double[] values = { 45, 45, 45, 45, 45 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 45 > limits.upperBCLimit();
		assert 45 < limits.upperABLimit();
		ControlChart controlChart = new ControlChartZoneB();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeLowerB() {
		double[] values = { 15, 15, 15, 15, 25 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 25 > limits.lowerBCLimit();
		ControlChart controlChart = new ControlChartZoneB();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeUpperB() {
		double[] values = { 45, 45, 45, 45, 35 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 35 < limits.upperBCLimit();
		ControlChart controlChart = new ControlChartZoneB();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllLowerBOrUnder() {
		double[] values = { 15, 5, 5, -5, -5 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartZoneB();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllUpperBOrOver() {
		double[] values = { 45, 55, 55, 65, 65 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartZoneB();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeUnderSomeOverB() {
		double[] values = { 45, 45, 45, 15, 15 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartZoneB();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeC() {
		double[] values = { 45, 45, 15, 25, 35 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartZoneB();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}
}
