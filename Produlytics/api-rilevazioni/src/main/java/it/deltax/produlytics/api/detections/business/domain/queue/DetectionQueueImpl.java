package it.deltax.produlytics.api.detections.business.domain.queue;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerie;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerieFactory;

import java.util.concurrent.TimeUnit;

// Implementazione di riferimento di `DetectionQueue`.
public class DetectionQueueImpl implements DetectionQueue {
	private final DetectionSerieFactory serieFactory;
	private final PublishProcessor<Detection> detectionProcessor;

	public DetectionQueueImpl(DetectionSerieFactory serieFactory) {
		this.serieFactory = serieFactory;
		this.detectionProcessor = PublishProcessor.create();

		// Imposta il publish processor per:
		// - dividere le rilevazioni in gruppi in base alla loro caratteristica (1 gruppo per caratteristica)
		// - processare ogni gruppo in modo sequenziale con `this.handleDetectionGroup`
		// TODO: Fix warning subscribe ignored
		this.detectionProcessor.observeOn(Schedulers.computation())
			.groupBy(Detection::characteristicId)
			.subscribe(group -> this.handleDetectionGroup(group.getKey(), group));
	}

	// Invia le rilevazioni in entrata al PublishProcessor.
	// `onNext` non è thread-safe, e `enqueueDetection` potrebbe essere chiamato da più thread,
	// quindi questo metodo deve essere marcato synchronized.
	@Override
	public synchronized void enqueueDetection(Detection detection) {
		this.detectionProcessor.onNext(detection);
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
		Single<DetectionSerie> serieSingle = this.createSerieForKey(key);
		// TODO: Fix warning subscribe ignored
		group.observeOn(Schedulers.computation())
			.timeout(30, TimeUnit.SECONDS, Flowable.empty())
			.concatMapCompletable(detection -> this.handleDetection(serieSingle, detection))
			.subscribe();
	}

	// Crea una `DetectionSerie` per la data caratteristica.
	// Ritorna un `Single` perchè la sua creazione può richiedere una chiamata al database,
	// e questa non dovrebbe bloccare il subscribe del `group` in `handleDetectionGroup`.
	// `.cache()` è importante per non ricreare la `DetectionSerie` per ogni rilevazione.
	private Single<DetectionSerie> createSerieForKey(CharacteristicId key) {
		return Single.fromCallable(() -> this.serieFactory.createSerie(key))
			.cache()
			.subscribeOn(Schedulers.io());
	}

	// Gestisce una singola rilevazione, semplicemente chiamando `DetectionSerie::insertDetection`.
	// Note importanti:
	// - ritorna un `Completable` su cui è settato `subscribeOn(Schedulers.io())` così che ogni
	//   rilevazione possa essere gestita su un thread diverso (altrimenti tutte le rilevazione di una
	//   stessa caratteristica saranno gestite sullo stesso thread).
	// - viene usato Schedulers.io() invece che Schedulers.computation() perchè il grosso del lavoro
	//   sarà aspettare la risposta a chiamate al database.
	private Completable handleDetection(Single<DetectionSerie> serieSingle, Detection detection) {
		return serieSingle.flatMapCompletable(serie -> Completable.fromAction(() -> serie.insertDetection(detection)))
			.subscribeOn(Schedulers.io());
	}
}
