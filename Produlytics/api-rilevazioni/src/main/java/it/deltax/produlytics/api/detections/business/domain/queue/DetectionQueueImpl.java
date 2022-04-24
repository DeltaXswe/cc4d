package it.deltax.produlytics.api.detections.business.domain.queue;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.series.DetectionSeries;
import it.deltax.produlytics.api.detections.business.domain.series.DetectionSeriesFactory;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Questa classe si occupa di accodare una rilevazione per essere processata successivamente in
 * background, senza quindi bloccare l'utilizzatore.
 */
public class DetectionQueueImpl implements DetectionQueue {
  /** La factory per creare una `DetectionSeries` per ogni caratteristica in coda. */
  private final DetectionSeriesFactory seriesFactory;
  /**
   * Un canale di RxJava 3 che si occupa di raggruppare le rilevazioni e processarle in differenti
   * thread.
   */
  private final FlowableProcessor<Detection> detectionProcessor;

  /** Una primitiva di sincronizzazione che coordina la chiusura della coda. */
  private final Phaser groupPhaser;

  /**
   * Crea una nuova istanza di `DetectionQueueImpl`.
   *
   * @param seriesFactory Il valore per il campo `seriesFactory`.
   */
  public DetectionQueueImpl(DetectionSeriesFactory seriesFactory) {
    this.seriesFactory = seriesFactory;
    // `.toSerialized()` è necessario perchè `this.detectionProcessor.onNext` potrebbe essere
    // chiamato da più
    // thread, e `PublishProcessor` non è thread-safe.
    this.detectionProcessor = PublishProcessor.<Detection>create().toSerialized();
    this.groupPhaser = new Phaser();

    // Imposta `detectionProcessor` per:
    // - dividere le rilevazioni in gruppi in base alla loro caratteristica (1 gruppo per
    // caratteristica)
    // - processare ogni gruppo in modo sequenziale con `this.handleDetectionGroup`
    // TODO: Fix warning subscribe ignored
    this.detectionProcessor
        .groupBy(Detection::characteristicId)
        .subscribe(group -> this.handleDetectionGroup(group.getKey(), group));
  }

  /**
   * Questo metodo implementa l'omonimo metodo definito in `DetectionQueue`.
   *
   * @param detection La rilevazione da accodare.
   */
  @Override
  public void enqueueDetection(Detection detection) {
    // TODO: Gestisci back-pressure
    this.detectionProcessor.onNext(detection);
  }

  /** Questo metodo implementa l'omonimo metodo definito in `DetectionQueue`. */
  @Override
  public void close() {
    // Completa il FlowableProcessor e impedisci nuovi inserimenti.
    this.detectionProcessor.onComplete();
    // Registra questo thread nel Phaser per segnalare la sua presenza.
    this.groupPhaser.register();
    // Poi aspetta che tutti gli altri membri del Phaser siano completi.
    this.groupPhaser.arriveAndAwaitAdvance();
  }

  /**
   * Questo metodo si occupa di gestire una serie di rilevazioni relative a una caratteristica.
   *
   * @param key L'identificativo globale della caratteristica.
   * @param group Una serie di rilevazioni della caratteristica identificata da `key` da analizzare.
   */
  private void handleDetectionGroup(CharacteristicId key, Flowable<Detection> group) {

    // Gestisce ogni gruppo/caratteristica:
    // - crea una `DetectionSeries` per quel gruppo/caratteristica, ma non aspetta il suo
    // completamento;
    // - imposta un timeout, che viene attivato dopo 30 secondi che non vengono ricevute rilevazioni
    //   per quella caratteristica;
    // - gestisce la singola rilevazione con `this.handleDetection`.
    //
    // Note importanti:
    // - il parametro `Flowable.empty()` di `timeout` evita di lanciare errori in caso di timeout;
    // - `concatMapCompletable` permette di finire di gestire una rilevazione prima della prossima.

    // Registra il gruppo nel Phaser, segnalandone quindi la sua esistenza.
    this.groupPhaser.register();

    // Crea una `DetectionSeries` per la data caratteristica.
    // Ritorna un `Single` perchè la sua creazione può richiedere una chiamata al database,
    // e questa non dovrebbe bloccare il subscribe del `group` in `handleDetectionGroup`.
    // `.cache()` è importante per non ricreare la `DetectionSeries` per ogni rilevazione.
    DetectionSeries series = this.seriesFactory.createSeries(key);

    // TODO: Fix warning subscribe ignored
    group
        .observeOn(Schedulers.computation())
        .timeout(300, TimeUnit.SECONDS, Flowable.empty())
        // Segnala il completamento di questo gruppo al Phaser, senza aspettare gli altri.
        .doFinally(this.groupPhaser::arriveAndDeregister)
        .subscribe(series::insertDetection);
  }
}
