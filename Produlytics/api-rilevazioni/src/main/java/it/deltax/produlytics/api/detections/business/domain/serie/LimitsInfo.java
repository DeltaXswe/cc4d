package it.deltax.produlytics.api.detections.business.domain.serie;

import java.util.Optional;

// Informazioni di una caratteristica per calcolare i limiti di controllo.
public record LimitsInfo(Optional<TechnicalLimits> technicalLimits, Optional<MeanStddev> meanStddev) {}
