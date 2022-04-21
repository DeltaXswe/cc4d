package it.deltax.produlytics.api.detections.business.domain.limits;

import java.util.Optional;

/**
 * Questo record rappresenta i limiti tecnici e di processo di una caratteristica, se presenti.
 * Almeno uno dei due tipi di limiti deve essere presente.
 *
 * @param technicalLimits I limiti tecnici, se esistono.
 * @param meanStddev I limiti di processo, se esistono.
 */
public record LimitsInfo(
  Optional<TechnicalLimits> technicalLimits,
  Optional<MeanStddev> meanStddev)
{}
