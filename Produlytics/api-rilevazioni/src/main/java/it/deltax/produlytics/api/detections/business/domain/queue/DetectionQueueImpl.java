package it.deltax.produlytics.api.detections.business.domain.queue;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;
import it.deltax.produlytics.api.detections.business.domain.cache.DetectionSerie;
import it.deltax.produlytics.api.detections.business.domain.cache.DetectionSerieFactory;

import java.util.concurrent.TimeUnit;

public class DetectionQueueImpl implements DetectionQueue {
	private final DetectionSerieFactory serieFactory;
	private final PublishProcessor<RawDetection> detectionProcessor;

	public DetectionQueueImpl(DetectionSerieFactory serieFactory) {
		this.serieFactory = serieFactory;
		this.detectionProcessor = PublishProcessor.create();

		// TODO: Fix warning subscribe ignored
		this.detectionProcessor.observeOn(Schedulers.computation())
			.groupBy(detection -> new CharacteristicKey(detection.deviceId(), detection.characteristicId()))
			.subscribe(group -> this.handleDetectionGroup(group.getKey(), group));
	}

	@Override
	public void enqueueDetection(RawDetection detection) {
		this.detectionProcessor.onNext(detection);
	}

	private void handleDetectionGroup(CharacteristicKey key, Flowable<RawDetection> group) {
		Single<DetectionSerie> serieSingle = this.createSerieForKey(key);
		group.observeOn(Schedulers.computation())
			.timeout(30, TimeUnit.SECONDS, Flowable.empty())
			.concatMapCompletable(detection -> this.handleDetection(serieSingle, detection))
			.subscribe();
	}

	private Single<DetectionSerie> createSerieForKey(CharacteristicKey key) {
		return Single.fromCallable(() -> this.serieFactory.createSerie(key.deviceId(), key.characteristicId()))
			.cache()
			.subscribeOn(Schedulers.io());
	}

	private Completable handleDetection(Single<DetectionSerie> serieSingle, RawDetection detection) {
		return serieSingle.flatMapCompletable(serie -> Completable.fromAction(() -> serie.insertDetection(detection)))
			.subscribeOn(Schedulers.computation());
	}
}
