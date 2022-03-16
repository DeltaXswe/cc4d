package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utils {
	public static <T> Stream<List<T>> windows(List<T> list, int size) {
		return IntStream.range(0, list.size() - size + 1).mapToObj(i -> list.subList(i, i + size));
	}
}
