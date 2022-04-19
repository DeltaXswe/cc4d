package it.deltax.produlytics.api.detections.business.domain.queue;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerie;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerieFactory;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

// Implementazione di riferimento di `DetectionQueue`.
public class DetectionQueueImpl implements DetectionQueue {
	private final DetectionSerieFactory serieFactory;
	private final FlowableProcessor<Detection> detectionProcessor;
	// Questo phaser è usato per coordinare il termine dei vari gruppi di `detectionProcessor`.
	private final Phaser groupPhaser;

	public DetectionQueueImpl(DetectionSerieFactory serieFactory) {
		this.serieFactory = serieFactory;
		// `.toSerialized()` è necessario perchè `this.detectionProcessor.onNext` potrebbe essere chiamato da più
		// thread, e `PublishProcessor` non è thread-safe.
		this.detectionProcessor = PublishProcessor.<Detection>create().toSerialized();
		this.groupPhaser = new Phaser();

		// Imposta `detectionProcessor` per:
		// - dividere le rilevazioni in gruppi in base alla loro caratteristica (1 gruppo per caratteristica)
		// - processare ogni gruppo in modo sequenziale con `this.handleDetectionGroup`
		// TODO: Fix warning subscribe ignored
		this.detectionProcessor.groupBy(Detection::characteristicId)
			.subscribe(group -> this.handleDetectionGroup(group.getKey(), group));
	}

	// Invia le rilevazioni in entrata al PublishProcessor.
	// `onNext` non è thread-safe, e `enqueueDetection` potrebbe essere chiamato da più thread,
	// quindi questo metodo deve essere marcato synchronized.
	@Override
	public void enqueueDetection(Detection detection) {
		// TODO: Gestisci back-pressure
		this.detectionProcessor.onNext(detection);
	}

	// Aspetta che tutte le rilevazioni siano processate.
	// Spring vede questo metodo e lo chiama automaticamente quando il programma sta per uscire.
	@Override
	public void close() {
		// Completa il FlowableProcessor e impedisci nuovi inserimenti.
		this.detectionProcessor.onComplete();
		// Registra questo thread nel Phaser per segnalare la sua presenza.
		this.groupPhaser.register();
		// Poi aspetta che tutti gli altri membri del Phaser siano completi.
		this.groupPhaser.arriveAndAwaitAdvance();
	}

	// Gestisce ogni gruppo/caratteristica:
	// - crea una `DetectionSerie` per quel gruppo/caratteristica, ma non aspetta il suo completamento;
	// - imposta un timeout, che viene attivato dopo 30 secondi che non vengono ricevute rilevazioni
	//   per quella caratteristica;
	// - gestisce la singola rilevazione con `this.handleDetection`.
	//
	// Note importanti:
	// - il parametro `Flowable.empty()` di `timeout` evita di lanciare errori in caso di timeout;
	// - `concatMapCompletable` permette di finire di gestire una rilevazione prima della prossima.
	private void handleDetectionGroup(CharacteristicId key, Flowable<Detection> group) {
		// Registra il gruppo nel Phaser, segnalandone quindi la sua esistenza.
		this.groupPhaser.register();

        // Crea una `DetectionSerie` per la data caratteristica.
        // Ritorna un `Single` perchè la sua creazione può richiedere una chiamata al database,
        // e questa non dovrebbe bloccare il subscribe del `group` in `handleDetectionGroup`.
        // `.cache()` è importante per non ricreare la `DetectionSerie` per ogni rilevazione.
		DetectionSerie serie = this.serieFactory.createSerie(key);

		// TODO: Fix warning subscribe ignored
		group.observeOn(Schedulers.computation())
			.timeout(300, TimeUnit.SECONDS, Flowable.empty())
			// Segnala il completamento di questo gruppo al Phaser, senza aspettare gli altri.
			.doFinally(this.groupPhaser::arriveAndDeregister)
			.subscribe(serie::insertDetection);
	}
}
