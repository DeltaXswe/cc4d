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
 * @param lowerLimit il limite di controllo inferiore
 * @param lowerLimit il limite di controllo inferiore
 */
public record ControlLimits(double lowerLimit, double upperLimit) {
  /**
   * Ritorna la media dei limiti.
   *
   * @return la media dei limiti
   */
  public double mean() {
    return (this.lowerLimit() + this.upperLimit()) / 2;
  }

  /**
   * Ritorna il valore limite tra la zona B e la zona C inferiori.
   *
   * @return il valore limite tra la zona B e la zona C inferiori
   */
  public double lowerBCLimit() {
    return this.mean() - this.stddev();
  }

  /**
   * Ritorna il valore limite tra la zona B e la zona C superiori.
   *
   * @return il valore limite tra la zona B e la zona C superiori
   */
  public double upperBCLimit() {
    return this.mean() + this.stddev();
  }

  /**
   * Ritorna il valore limite tra la zona A e la zona B inferiori.
   *
   * @return il valore limite tra la zona A e la zona B inferiori
   */
  public double lowerABLimit() {
    return this.mean() - 2 * this.stddev();
  }

  /**
   * Ritorna il valore limite tra la zona A e la zona B superiori.
   *
   * @return il valore limite tra la zona A e la zona B superiori
   */
  public double upperABLimit() {
    return this.mean() + 2 * this.stddev();
  }

  /**
   * Questo metodo ritorna la deviazione standard, ovvero un sesto della differenza tra {@code upperLimit}
   * e {@code lowerLimit}.
   *
   * @return la deviazione standard
   */
  private double stddev() {
    return (this.upperLimit() - this.lowerLimit()) / 6;
  }
}
