package it.deltax.produlytics.api.detections.business.domain.charts;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MarkableDetectionMock implements MarkableDetection {
  private final double value;
  private boolean outlier;

  public static List<MarkableDetectionMock> listFromValues(double... values) {
    return Arrays.stream(values)
        .mapToObj(value -> new MarkableDetectionMock(value, false))
        .toList();
  }

  @Override
  public void markOutlier() {
    this.outlier = true;
  }

  @Override
  public double value() {
    return this.value;
  }

  public boolean isOutlier() {
    return this.outlier;
  }
}
