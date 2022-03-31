package it.deltax.produlytics.api.unit.control_chart;

import it.deltax.produlytics.api.detections.business.domain.control_chart.Utils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UtilsTest {
	@Test
	void testWindows() {
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
		List<List<Integer>> windows = Utils.windows(list, 3).toList();
		List<List<Integer>> expected = List.of(List.of(1, 2, 3),
			List.of(2, 3, 4),
			List.of(3, 4, 5),
			List.of(4, 5, 6),
			List.of(5, 6, 7)
		);
		assert windows.equals(expected);
	}
}
