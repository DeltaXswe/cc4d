package it.deltax.produlytics.api.unit.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.unit.detections.business.domain.charts.MarkableDetectionMock;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartZoneC;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ControlChartZoneCTest {
  @Test
  void testRequiredDetections() {
    ControlChart controlChart = new ControlChartZoneC();
    assert controlChart.requiredDetectionCount() == 7;
  }

  @Test
  void testAllLowerC() {
    double[] values = {25, 25, 25, 25, 25, 25, 25};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    assert 25 < limits.mean();
    assert 25 > limits.lowerBCLimit();
    ControlChart controlChart = new ControlChartZoneC();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testAllUpperC() {
    double[] values = {35, 35, 35, 35, 35, 35, 35};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    assert 35 > limits.mean();
    assert 35 < limits.upperBCLimit();
    ControlChart controlChart = new ControlChartZoneC();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testAllUnderMean() {
    double[] values = {25, 25, 25, 25, 15, 5, -5};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartZoneC();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testAllOverMean() {
    double[] values = {35, 35, 35, 35, 45, 55, 65};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartZoneC();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testSomeUnderSomeOverMean() {
    double[] values = {35, 35, 35, 35, 15, 15, 15};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartZoneC();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
  }
}
