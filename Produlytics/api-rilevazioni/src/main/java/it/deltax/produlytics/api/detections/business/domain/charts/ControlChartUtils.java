package it.deltax.produlytics.api.detections.business.domain.charts;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ControlChartUtils {
	// ControlChartUtils non è instanziabile, è solo una collezione di metodi statici di supporto
	private ControlChartUtils() {}

	// Ritorna uno stream di "finestre" di larghezza `size` data una lista `list`. Ad esempio, dato:
	//   list = [1, 2, 3, 4, 5, 6, 7], size = 3
	// vengono restituiti:
	//          [1, 2, 3]
	//             [2, 3, 4]
	//                [3, 4, 5]
	//                   [4, 5, 6]
	//                      [5, 6, 7]
	public static <T> Stream<List<T>> windows(List<T> list, int size) {
		return IntStream.range(0, list.size() - size + 1).mapToObj(i -> list.subList(i, i + size));
	}
}
