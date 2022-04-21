package it.deltax.produlytics.api.exceptions;

/** Questa enumerazione rappresenta le possibili tipologie di errore. */
public enum ErrorType {
  /** Caratterizza gli errori di autenticazione. */
  AUTHENTICATION,
  /** Caratterizza gli errori dovuti a macchine o caratteristiche non esistenti. */
  NOT_FOUND,
  /** Caratterizza gli errori dovuti a macchine o caratteristiche archiviate o disattivate. */
  ARCHIVED
}
