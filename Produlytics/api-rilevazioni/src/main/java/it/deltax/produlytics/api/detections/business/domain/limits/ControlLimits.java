package it.deltax.produlytics.api.detections.business.domain.limits;

/*
   ------------------- <- upperLimit()
     Zona C superiore
   ------------------- <- upperBCLimit()
     Zona B superiore
   ------------------- <- upperABLimit()
     Zona A superiore
   ------------------- <- media                +
     Zona A inferiore                          | <- stddev()
   ------------------- <- lowerABLimit()       +
     Zona B inferiore
   ------------------- <- lowerBCLimit()
     Zona C inferiore
   ------------------- <- lowerLimit()
*/
/**
 * Questa record rappresenta dei limiti di controllo.
 *
 * @param lowerLimit Il limite di controllo inferiore.
 * @param upperLimit Il limite di controllo superiore.
 */
public record ControlLimits(double lowerLimit, double upperLimit) {
  /**
   * Ritorna la media dei limiti.
   *
   * @return La media dei limiti.
   */
  public double mean() {
    return (this.lowerLimit() + this.upperLimit()) / 2;
  }

  /**
   * Ritorna il valore limite tra la zona B e la zona C inferiori.
   *
   * @return Il valore limite tra la zona B e la zona C inferiori.
   */
  public double lowerBCLimit() {
    return this.mean() - this.stddev();
  }

  /**
   * Ritorna il valore limite tra la zona B e la zona C superiori.
   *
   * @return Il valore limite tra la zona B e la zona C superiori.
   */
  public double upperBCLimit() {
    return this.mean() + this.stddev();
  }

  /**
   * Ritorna il valore limite tra la zona A e la zona B inferiori.
   *
   * @return Il valore limite tra la zona A e la zona B inferiori.
   */
  public double lowerABLimit() {
    return this.mean() - 2 * this.stddev();
  }

  /**
   * Ritorna il valore limite tra la zona A e la zona B superiori.
   *
   * @return Il valore limite tra la zona A e la zona B superiori.
   */
  public double upperABLimit() {
    return this.mean() + 2 * this.stddev();
  }

  /**
   * Questo metodo ritorna la deviazione standard, ovvero un sesto della differenza tra `upperLimit`
   * e `lowerLimit`.
   *
   * @return La deviazione standard.
   */
  private double stddev() {
    return (this.upperLimit() - this.lowerLimit()) / 6;
  }
}
