package it.deltax.produlytics.api.detections.business.domain;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Implementazione di alcune funzioni comuni sulle liste.
public class Utils {
	public static <T> List<T> lastN(List<T> list, int size) {
		return list.subList(list.size() - size, list.size());
	}

	public static <T> Stream<List<T>> windows(List<T> list, int size) {
		return IntStream.range(0, size).mapToObj(i -> list.subList(i, i + size));
	}
}
