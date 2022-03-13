package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.LimitsInfo;

// Informazioni di una caratteristica utili per la validazione.
public record CharacteristicInfo(boolean archived, LimitsInfo limitsInfo) {}
