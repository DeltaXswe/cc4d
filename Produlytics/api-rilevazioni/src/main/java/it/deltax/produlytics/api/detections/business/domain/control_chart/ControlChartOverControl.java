package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.8.
// Identifica se 14 punti consecutivi sono a zig-zag.
public class ControlChartOverControl implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 14;
	}

	@Override
	public void analyzeDetections(List<MarkableDetection> detections, ControlLimits limits) {
		// Integer.signum(Double.compare(x, y)) vale:
		// -1 se x < y
		// 0 se x == y
		// +1 se x > y
		//
		// cmp1 != cmp2 equivale quindi a verificare che l'ordine tra w0 e w1 sia diverso di quello tra w1 e w2,
		// cioè che w0, w1 e w2 siano a zig-zag.
		// Con Utils.windows questa proprietà viene verificata ogni per finestra di 3 punti consecutivi,
		// cioè per ogni coppia di segmenti collegati, quindi vale per tutti i punti di `detections`.
		boolean isOverControl = Utils.windows(detections, 3).allMatch(window -> {
			double w0 = window.get(0).value();
			double w1 = window.get(1).value();
			double w2 = window.get(2).value();
			int cmp1 = Integer.signum(Double.compare(w0, w1));
			int cmp2 = Integer.signum(Double.compare(w1, w2));
			return cmp1 != cmp2;
		});
		if(isOverControl) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
