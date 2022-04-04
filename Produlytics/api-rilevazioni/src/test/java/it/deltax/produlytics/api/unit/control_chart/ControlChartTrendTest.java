package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartTrend;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlLimits;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartTrendTest {
	@Test
	void testRequiredDetections() {
		ControlChart controlChart = new ControlChartTrend();
		assert controlChart.requiredDetectionCount() == 7;
	}

	@Test
	void testAllIncreasing() {
		double[] values = { 1, 2, 3, 4, 5, 6, 7 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartTrend();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testAllDecreasing() {
		double[] values = { 7, 6, 5, 4, 3, 2, 1 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartTrend();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testSomeIncreasingSomeDecreasing() {
		double[] values = { 1, 2, 3, 4, 3, 2, 1 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartTrend();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void test2Increasing2Decreasing() {
		double[] values = { 1, 2, 3, 2, 1, 2, 3 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 60);
		ControlChart controlChart = new ControlChartTrend();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}
}
