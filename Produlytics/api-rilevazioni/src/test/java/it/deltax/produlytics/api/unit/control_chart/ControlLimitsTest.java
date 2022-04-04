package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.charts.ControlLimits;
import org.junit.jupiter.api.Test;

public class ControlLimitsTest {
	@Test
	void testControlLimits() {
		ControlLimits limits = new ControlLimits(0, 60);
		assert limits.lowerLimit() == 0;
		assert limits.lowerABLimit() == 10;
		assert limits.lowerBCLimit() == 20;
		assert limits.mean() == 30;
		assert limits.upperBCLimit() == 40;
		assert limits.upperABLimit() == 50;
		assert limits.upperLimit() == 60;
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
