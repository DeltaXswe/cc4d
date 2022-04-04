package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.8.
// Identifica se 14 punti consecutivi sono a zig-zag.
public class ControlChartOverControl implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 14;
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> detections, ControlLimits limits) {
		// Per ogni tripletta di punti consecutivi w0, w1 e w2 controlla se formino una forma a V o V rovesciata.
		// Poichè le triplette si sovrappongono l'alternamento deve continuare allo stesso modo in tutta la sequenza
		// per poter risultare true alla fine.
		boolean isOverControl = Utils.windows(detections, 3).allMatch(window -> {
			double w0 = window.get(0).value();
			double w1 = window.get(1).value();
			double w2 = window.get(2).value();
			boolean incDec = (w0 < w1) && (w1 > w2);
			boolean decInc = (w0 > w1) && (w1 < w2);
			return incDec || decInc;
		});
		if(isOverControl) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
