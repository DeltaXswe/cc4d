package it.deltax.produlytics.api.detections.business.domain.charts;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** Questa classe implementa dei metodi di supporto per le carte di controllo. */
public class ControlChartUtils {
  /**
   * Questo costruttore privato non è utilizzabile, segnala solo che questa classe non è
   * instanziabile.
   */
  private ControlChartUtils() {}

  /**
   * Questo metodo ritorna uno {@code Stream} di "finestre" della lista sorgente, cioè uno {@code Stream} di
   * tutte le liste contenenti valori adiacenti nella lista sorgente.
   *
   * @param <T> il tipo dei valori della lista
   * @param <T> il tipo dei valori della lista
   * @param <T> il tipo dei valori della lista
   * @return uno stream di finestre della lista {@code list} di dimensione {@code size}
   */
  public static <T> Stream<List<T>> windows(List<T> list, int size) {
    /*
    Ad esempio, dato:
        list = [1, 2, 3, 4, 5, 6, 7], size = 3
    vengono restituiti:
               [1, 2, 3]
                  [2, 3, 4]
                     [3, 4, 5]
                        [4, 5, 6]
                           [5, 6, 7]
    */
    return IntStream.range(0, list.size() - size + 1).mapToObj(i -> list.subList(i, i + size));
  }

  /**
   * Questo metodo marca tutte le rilevazioni della lista.
   *
   * @param detections una lista di rilevazioni marcabili
   */
  public static void markAll(List<? extends MarkableDetection> detections) {
    detections.forEach(MarkableDetection::markOutlier);
  }
}
