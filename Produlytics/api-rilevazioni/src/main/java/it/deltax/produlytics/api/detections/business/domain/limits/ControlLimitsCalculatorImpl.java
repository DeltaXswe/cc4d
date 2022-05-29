package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.ports.out.FindLimitsPort;
import lombok.AllArgsConstructor;

/** Questa classe si occupa di calcolare i limiti di controllo di una caratteristica. */
@AllArgsConstructor
public class ControlLimitsCalculatorImpl implements ControlLimitsCalculator {
  /** La porta per ottenere i limiti tecnici e di processo di una caratteristica. */
  private final FindLimitsPort findLimitsPort;

  /**
   * Implementazione dell'omonimo metodo definito in {@code ControlLimitsCalculator}.
   *
   * @param characteristicId l'identificativo globale della caratteristica di cui calcolare i limiti
   *     di controllo
   * @return i limiti di controllo della caratteristica identificata da {@code characteristicId}
   */
  @Override
  public ControlLimits calculateControlLimits(CharacteristicId characteristicId) {
    LimitsInfo limitsInfo = this.findLimitsPort.findLimits(characteristicId);
    // Prima controlla i limiti di processo.
    if (limitsInfo.meanStddev().isPresent()) {
      return this.fromMeanStddev(limitsInfo.meanStddev().get());
    } else if (limitsInfo.technicalLimits().isPresent()) { // Se no controlla i limiti tecnici
      return this.fromTechnicalLimits(limitsInfo.technicalLimits().get());
    } else {
      throw new IllegalStateException(
          "Non sono impostati nè i limiti tecnici nè l'auto-adjust per la caratteristica"
              + characteristicId);
    }
  }

  /**
   * Questo metodo crea una nuova istanza di {@code ControlLimits} utilizzando un'istanza di {@code MeanStddev}.
   *
   * @param meanStddev i limiti di processo
   * @return i limiti di controllo derivanti dai limiti di processo
   */
  private ControlLimits fromMeanStddev(MeanStddev meanStddev) {
    double lowerLimit = meanStddev.mean() - 3 * meanStddev.stddev();
    double upperLimit = meanStddev.mean() + 3 * meanStddev.stddev();
    return new ControlLimits(lowerLimit, upperLimit);
  }

  /**
   * Questo metodo crea una nuova istanza di {@code ControlLimits} utilizzando un'istanza di
   * {@code TechnicalLimits}.
   *
   * @param technicalLimits i limiti tecnici
   * @return i limiti di controllo derivanti dai limiti tecnici
   */
  private ControlLimits fromTechnicalLimits(TechnicalLimits technicalLimits) {
    return new ControlLimits(technicalLimits.lowerLimit(), technicalLimits.upperLimit());
  }
}
