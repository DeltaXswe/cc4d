package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ControlChartsGroupTest {
  @Test
  void testCorrectRequiredDetectionCount() {
    ControlChartsGroup controlChartsGroup =
        new ControlChartsGroupImpl(
            List.of(new FakeControlChart(3), new FakeControlChart(5), new FakeControlChart(2)));

    assert controlChartsGroup.requiredDetectionCount() == 5;
  }

  @Test
  void testForwardAll() {
    List<FakeControlChart> fakeControlCharts =
        List.of(new FakeControlChart(3), new FakeControlChart(5), new FakeControlChart(2));
    ControlChartsGroup controlChartsGroup = new ControlChartsGroupImpl(fakeControlCharts);
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(7, 9, 5, 2, 1);
    ControlLimits controlLimits = new ControlLimits(0, 100);
    controlChartsGroup.analyzeDetections(detections, controlLimits);
    assert fakeControlCharts.stream().allMatch(FakeControlChart::wasCalled);
  }

  @Test
  void testSomeMissed() {
    List<FakeControlChart> fakeControlCharts =
        List.of(new FakeControlChart(3), new FakeControlChart(9), new FakeControlChart(2));
    ControlChartsGroup controlChartsGroup = new ControlChartsGroupImpl(fakeControlCharts);
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(7, 9, 5, 2, 1);
    ControlLimits controlLimits = new ControlLimits(0, 100);
    controlChartsGroup.analyzeDetections(detections, controlLimits);
    assert fakeControlCharts.get(0).wasCalled();
    assert !fakeControlCharts.get(1).wasCalled();
    assert fakeControlCharts.get(2).wasCalled();
  }

  @RequiredArgsConstructor
  private static class FakeControlChart implements ControlChart {
    private final int detectionCount;
    private boolean called = false;

    @Override
    public int requiredDetectionCount() {
      return this.detectionCount;
    }

    @Override
    public void analyzeDetections(
        List<? extends MarkableDetection> detections, ControlLimits limits) {
      this.called = true;
    }

    boolean wasCalled() {
      return this.called;
    }
  }
}
