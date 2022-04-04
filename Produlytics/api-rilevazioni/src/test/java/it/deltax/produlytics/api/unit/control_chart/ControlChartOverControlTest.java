package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartOverControl;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartOverControlTest {
	@Test
	void testRequiredDetections() {
		ControlChart controlChart = new ControlChartOverControl();
		assert controlChart.requiredDetectionCount() == 14;
	}

	@Test
	void testIncreaseDecrease() {
		double[] values = { 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 10);
		ControlChart controlChart = new ControlChartOverControl();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testDecreaseIncrease() {
		double[] values = { 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 10);
		ControlChart controlChart = new ControlChartOverControl();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionHelper::isOutlier);
	}

	@Test
	void testOneSameOrder() {
		double[] values = { 2, 0, 2, 0, 2, 0, 2, 2, 0, 2, 0, 2, 0, 2 };
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 10);
		ControlChart controlChart = new ControlChartOverControl();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionHelper::isOutlier);
	}
}
