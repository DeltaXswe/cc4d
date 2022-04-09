package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetectionMock;
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
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 10);
		ControlChart controlChart = new ControlChartOverControl();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testDecreaseIncrease() {
		double[] values = { 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 10);
		ControlChart controlChart = new ControlChartOverControl();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testOneSameOrder() {
		double[] values = { 2, 0, 2, 0, 2, 0, 2, 2, 0, 2, 0, 2, 0, 2 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 10);
		ControlChart controlChart = new ControlChartOverControl();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
	}

	@Test
	void testOneSameOrder2() {
		double[] values = { 2, 1, 1, 0, 2, 0, 2, 2, 0, 2, 0, 2, 0, 2 };
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
		ControlLimits limits = new ControlLimits(0, 10);
		ControlChart controlChart = new ControlChartOverControl();
		controlChart.analyzeDetections(detections, limits);
		assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
	}
}
