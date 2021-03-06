package it.deltax.produlytics.api.unit.detections.business.domain.charts.impls;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlChart;
import it.deltax.produlytics.api.unit.detections.business.domain.charts.MarkableDetectionMock;
import it.deltax.produlytics.api.detections.business.domain.charts.impls.ControlChartTrend;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ControlChartTrendTest {
  @Test
  void testRequiredDetections() {
    ControlChart controlChart = new ControlChartTrend();
    assert controlChart.requiredDetectionCount() == 7;
  }

  @Test
  void testAllIncreasing() {
    double[] values = {1, 2, 3, 4, 5, 6, 7};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartTrend();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testAllDecreasing() {
    double[] values = {7, 6, 5, 4, 3, 2, 1};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartTrend();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void testSomeIncreasingSomeDecreasing() {
    double[] values = {1, 2, 3, 4, 3, 2, 1};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartTrend();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void test2Increasing2Decreasing() {
    double[] values = {1, 2, 3, 2, 1, 2, 3};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartTrend();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
  }

  @Test
  void test2Decreasing2Increasing() {
    double[] values = {3, 2, 1, 2, 3, 2, 1};
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(values);
    ControlLimits limits = new ControlLimits(0, 60);
    ControlChart controlChart = new ControlChartTrend();
    controlChart.analyzeDetections(detections, limits);
    assert detections.stream().noneMatch(MarkableDetectionMock::isOutlier);
  }
}
