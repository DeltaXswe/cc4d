package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartZoneC;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlLimits;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartZoneCTest {
	@Test
	void testRequiredDetections() {
		ControlChart controlChart = new ControlChartZoneC();
		assert controlChart.requiredDetectionCount() == 7;
	}

	@Test
	void testAllUnderMean() {
		double[] values = { 25, 25, 25, 25, 25, 25, 25 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 25 < limits.mean();
		assert 25 > limits.lowerBCLimit();
		ControlChart controlChart = new ControlChartZoneC();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllOverMean() {
		double[] values = { 35, 35, 35, 35, 35, 35, 35 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		assert 35 > limits.mean();
		assert 35 < limits.upperBCLimit();
		ControlChart controlChart = new ControlChartZoneC();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllUnderMeanOrUnder() {
		double[] values = { 25, 25, 25, 25, 15, 5, -5 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartZoneC();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllOverMeanOrOver() {
		double[] values = { 35, 35, 35, 35, 45, 55, 65 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartZoneC();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeUnderSomeOverMean() {
		double[] values = { 35, 35, 35, 35, 15, 15, 15 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartZoneC();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}
}
