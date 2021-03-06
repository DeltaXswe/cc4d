package it.deltax.produlytics.api.unit.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.unit.detections.business.domain.charts.MarkableDetectionMock;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartZoneB;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ControlChartZoneBTest {
  @Test
  void testRequiredDetections() {
    ControlChart controlChart = new ControlChartZoneB();
    assert controlChart.requiredDetectionCount() == 5;
  }

  @Test
  void testAllLowerB() {
    double[] values = {15, 15, 15, 15, 15};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    assert 15 < limits.lowerBCLimit();
    assert 15 > limits.lowerABLimit();
    ControlChart controlChart = new ControlChartZoneB();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testAllUpperB() {
    double[] values = {45, 45, 45, 45, 45};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    assert 45 > limits.upperBCLimit();
    assert 45 < limits.upperABLimit();
    ControlChart controlChart = new ControlChartZoneB();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testSomeLowerB() {
    double[] values = {15, 15, 15, 15, 25};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    assert 25 > limits.lowerBCLimit();
    ControlChart controlChart = new ControlChartZoneB();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testSomeUpperB() {
    double[] values = {45, 45, 45, 45, 35};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    assert 35 < limits.upperBCLimit();
    ControlChart controlChart = new ControlChartZoneB();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testAllUnder() {
    double[] values = {15, 5, 5, -5, -5};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartZoneB();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testAllOver() {
    double[] values = {45, 55, 55, 65, 65};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartZoneB();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testSomeUnderSomeOverB() {
    double[] values = {45, 45, 45, 15, 15};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartZoneB();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testSomeC() {
    double[] values = {45, 45, 15, 25, 35};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartZoneB();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
  }
}
