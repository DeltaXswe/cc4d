package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartBeyondLimits;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlLimits;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartBeyondLimitsTest {
	@Test
	void testRequiredDetections() {
		ControlChart controlChart = new ControlChartBeyondLimits();
		assert controlChart.requiredDetectionCount() == 1;
	}

	@Test
	void testUnderLowerLimit() {
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(-500);
		ControlLimits limits = new ControlLimits(0, 100);
		assert -500 < limits.lowerLimit();
		ControlChart controlChart = new ControlChartBeyondLimits();
		controlChart.analyzeDetections(detections, limits);
		assert detections.get(0).isOutlier();
	}
	
	@Test
	void testOverUpperLimit() {
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(500);
		ControlLimits limits = new ControlLimits(0, 100);
		assert 500 > limits.upperLimit();
		ControlChart controlChart = new ControlChartBeyondLimits();
		controlChart.analyzeDetections(detections, limits);
		assert detections.get(0).isOutlier();
	}

	@Test
	void testInControlLimitsUnderMean() {
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(1);
		ControlLimits limits = new ControlLimits(0, 100);
		assert 1 > limits.lowerLimit();
		assert 1 < limits.upperLimit();
		ControlChart controlChart = new ControlChartBeyondLimits();
		controlChart.analyzeDetections(detections, limits);
		assert !detections.get(0).isOutlier();
	}

	@Test
	void testInControlLimitsOverMean() {
		List<MarkableDetectionHelper> detections = MarkableDetectionHelper.listFromValues(99);
		ControlLimits limits = new ControlLimits(0, 100);
		assert 99 > limits.lowerLimit();
		assert 99 < limits.upperLimit();
		ControlChart controlChart = new ControlChartBeyondLimits();
		controlChart.analyzeDetections(detections, limits);
		assert !detections.get(0).isOutlier();
	}
}