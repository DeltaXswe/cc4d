package it.deltax.produlytics.api.unit.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import org.junit.jupiter.api.Test;

public class ControlLimitsTest {
  @Test
  void testControlLimits() {
    ControlLimits controlLimits = new ControlLimits(0, 60);
    assert controlLimits.lowerLimit() == 0;
    assert controlLimits.lowerABLimit() == 10;
    assert controlLimits.lowerBCLimit() == 20;
    assert controlLimits.mean() == 30;
    assert controlLimits.upperBCLimit() == 40;
    assert controlLimits.upperABLimit() == 50;
    assert controlLimits.upperLimit() == 60;

    ControlLimits controlLimits2 = new ControlLimits(100, 700);
    assert controlLimits2.lowerLimit() == 100;
    assert controlLimits2.lowerABLimit() == 200;
    assert controlLimits2.lowerBCLimit() == 300;
    assert controlLimits2.mean() == 400;
    assert controlLimits2.upperBCLimit() == 500;
    assert controlLimits2.upperABLimit() == 600;
    assert controlLimits2.upperLimit() == 700;
  }

  @Test
  void testControlLimitsNegative() {
    ControlLimits limits = new ControlLimits(-60, 60);
    assert limits.lowerLimit() == -60;
    assert limits.lowerABLimit() == -40;
    assert limits.lowerBCLimit() == -20;
    assert limits.mean() == 0;
    assert limits.upperBCLimit() == 20;
    assert limits.upperABLimit() == 40;
    assert limits.upperLimit() == 60;
  }
}
