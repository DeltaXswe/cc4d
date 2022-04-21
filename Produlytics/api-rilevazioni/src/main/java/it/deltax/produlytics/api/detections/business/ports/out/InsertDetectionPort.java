package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.Detection;

/**
 * Questa interfaccia descrive l'abilità di poter persistere una nuova rilevazione e
 * permette di separare la sua implementazione dalla logica di business.
 */
public interface InsertDetectionPort {
	/**
	 * Questo metodo si occupa di persistere una rilevazione per poterla riottenere successivamente.
	 *
	 * @param detection La rilevazione da persistere.
	 */
	void insertDetection(Detection detection);
}
