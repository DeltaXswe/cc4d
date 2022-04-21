package it.deltax.produlytics.api.detections.business.domain.charts;

import java.util.List;
import org.junit.jupiter.api.Test;

public class ControlChartUtilsTest {
  @Test
  void testWindows() {
    List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
    List<List<Integer>> windows = ControlChartUtils.windows(list, 3).toList();
    List<List<Integer>> expected =
        List.of(
            List.of(1, 2, 3),
            List.of(2, 3, 4),
            List.of(3, 4, 5),
            List.of(4, 5, 6),
            List.of(5, 6, 7));
    assert windows.equals(expected);
  }

  @Test
  void testMarkAll() {
    List<MarkableDetectionMock> detections = MarkableDetectionMock.listFromValues(1, 2, 3);
    ControlChartUtils.markAll(detections);
    assert detections.stream().allMatch(MarkableDetectionMock::isOutlier);
  }
}
