package it.deltax.produlytics.api.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetectionMock;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ControlChartBeyondLimitsTest {
  @Test
  void testRequiredDetections() {
    ControlChart controlChart = new ControlChartBeyondLimits();
    assert controlChart.requiredDetectionCount() == 1;
  }

  @Test
  void testUnderLowerLimit() {
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(-500);
    ControlLimits limits = new ControlLimits(0, 100);
    assert -500 < limits.lowerLimit();
    ControlChart controlChart = new ControlChartBeyondLimits();
    controlChart.analyzeDetections(detections, limits);
    assert detections.get(0).isOutlier();
  }

  @Test
  void testOverUpperLimit() {
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(500);
    ControlLimits limits = new ControlLimits(0, 100);
    assert 500 > limits.upperLimit();
    ControlChart controlChart = new ControlChartBeyondLimits();
    controlChart.analyzeDetections(detections, limits);
    assert detections.get(0).isOutlier();
  }

  @Test
  void testInControlLimitsUnderMean() {
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(1);
    ControlLimits limits = new ControlLimits(0, 100);
    assert 1 > limits.lowerLimit();
    assert 1 < limits.upperLimit();
    ControlChart controlChart = new ControlChartBeyondLimits();
    controlChart.analyzeDetections(detections, limits);
    assert !detections.get(0).isOutlier();
  }

  @Test
  void testInControlLimitsOverMean() {
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(99);
    ControlLimits limits = new ControlLimits(0, 100);
    assert 99 > limits.lowerLimit();
    assert 99 < limits.upperLimit();
    ControlChart controlChart = new ControlChartBeyondLimits();
    controlChart.analyzeDetections(detections, limits);
    assert !detections.get(0).isOutlier();
  }
}
