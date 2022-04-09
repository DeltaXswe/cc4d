package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartsTest {
	@Test
	void testCorrectRequiredDetectionCount() {
		ControlCharts controlCharts = new ControlChartsImpl(List.of(new FakeControlChart(3),
			new FakeControlChart(5),
			new FakeControlChart(2)
		));

		assert controlCharts.requiredDetectionCount() == 5;
	}
	@Test
	void testForwardAll() {
		List<FakeControlChart> fakeControlCharts = List.of(new FakeControlChart(3),
			new FakeControlChart(5),
			new FakeControlChart(2)
		);
		ControlCharts controlCharts = new ControlChartsImpl(fakeControlCharts);
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(7, 9, 5, 2, 1);
		ControlLimits controlLimits = new ControlLimits(0, 100);
		controlCharts.analyzeDetections(detections, controlLimits);
		assert fakeControlCharts.stream().allMatch(FakeControlChart::wasCalled);
	}
	@Test
	void testSomeMissed() {
		List<FakeControlChart> fakeControlCharts = List.of(new FakeControlChart(3),
			new FakeControlChart(9),
			new FakeControlChart(2)
		);
		ControlCharts controlCharts = new ControlChartsImpl(fakeControlCharts);
		List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(7, 9, 5, 2, 1);
		ControlLimits controlLimits = new ControlLimits(0, 100);
		controlCharts.analyzeDetections(detections, controlLimits);
		assert fakeControlCharts.get(0).wasCalled();
		assert !fakeControlCharts.get(1).wasCalled();
		assert fakeControlCharts.get(2).wasCalled();
	}

	private static class FakeControlChart implements ControlChart {
		private final int detectionCount;
		private boolean called;

		public FakeControlChart(int detectionCount) {
			this.detectionCount = detectionCount;
			this.called = false;
		}

		@Override
		public int requiredDetectionCount() {
			return this.detectionCount;
		}

		@Override
		public void analyzeDetections(List<? extends MarkableDetection> lastDetections, ControlLimits limits) {
			this.called = true;
		}

		boolean wasCalled() {
			return this.called;
		}
	}
}
