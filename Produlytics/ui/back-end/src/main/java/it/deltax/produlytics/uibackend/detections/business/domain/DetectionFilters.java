package it.deltax.produlytics.uibackend.detections.business.domain;

import java.util.OptionalInt;
import java.util.OptionalLong;

public record DetectionFilters(
	OptionalLong olderThan, OptionalLong newerThan, OptionalInt limit
) {}
