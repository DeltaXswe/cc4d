package it.deltax.produlytics.api.detections.business.domain.charts;

import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;

import java.util.List;

// Implementazione della carta di controllo corrispondente al requisito ROF24.5.
// Identifica se 7 punti consecutivi seguono lo stesso ordine.
public class ControlChartTrend implements ControlChart {
	@Override
	public int requiredDetectionCount() {
		return 7;
	}

	@Override
	public void analyzeDetections(List<? extends MarkableDetection> detections, ControlLimits limits) {
		// Per ogni tripletta di punti consecutivi w0, w1 e w2 controlla se sono tutti nello stesso ordine.
		// Poichè le triplette si sovrappongono il trend deve continuare allo stesso modo in tutta la sequenza
		// per poter risultare true alla fine.
		boolean sameTrend = Utils.windows(detections,3).allMatch(window -> {
			double w0 = window.get(0).value();
			double w1 = window.get(1).value();
			double w2 = window.get(2).value();
			boolean alwaysInc = (w0 < w1) && (w1 < w2);
			boolean alwaysDec = (w0 > w1) && (w1 > w2);
			return alwaysInc || alwaysDec;
		});

		if(sameTrend) {
			detections.forEach(MarkableDetection::markOutlier);
		}
	}
}
