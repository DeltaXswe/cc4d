package it.deltax.produlytics.api.detections.business.domain.queue;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;
import it.deltax.produlytics.api.detections.business.domain.cache.DetectionCache;
import it.deltax.produlytics.api.detections.business.domain.cache.DetectionCacheFactory;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;

import java.util.concurrent.TimeUnit;

public class DetectionQueueImpl implements DetectionQueue {
	private final DetectionCacheFactory cacheFactory;
	private final ControlChart controlChart;
	private final PublishProcessor<RawDetection> detectionProcessor;

	public DetectionQueueImpl(DetectionCacheFactory cacheFactory, ControlChart controlChart) {
		this.cacheFactory = cacheFactory;
		this.controlChart = controlChart;
		this.detectionProcessor = PublishProcessor.create();

		this.detectionProcessor.observeOn(Schedulers.computation())
			.groupBy(detection -> new CharacteristicId(detection.deviceId(), detection.characteristicId()))
			.concatMapCompletable(group -> this.handleDetectionGroup(group.getKey(), group))
			.subscribe();
	}

	@Override
	public void enqueueDetection(RawDetection detection) {
		this.detectionProcessor.onNext(detection);
	}

	private DetectionCache createCacheForKey(CharacteristicId key) {
		return this.cacheFactory.createCache(key.deviceId(), key.id());
	}

	private Completable handleDetectionGroup(CharacteristicId key, Flowable<RawDetection> group) {
		return Single.fromCallable(() -> this.createCacheForKey(key))
			.subscribeOn(Schedulers.io())
			.doOnSuccess(cache -> this.subscribeToGroupWithCache(cache, group))
			.subscribeOn(Schedulers.computation())
			.ignoreElement();
	}

	private void subscribeToGroupWithCache(DetectionCache cache, Flowable<RawDetection> group) {
		group.observeOn(Schedulers.computation()).timeout(30, TimeUnit.SECONDS, Flowable.empty()).concatMapCompletable(
			detection -> this.handleDetection(cache, detection)).subscribe();
	}

	private Completable handleDetection(DetectionCache cache, RawDetection rawDetection) {
		return Completable.fromAction(() -> {
			cache.insertDetection(rawDetection);
			DetectionCacheAdapter ports = new DetectionCacheAdapter(cache);
			this.controlChart.analyzeDetection(cache.findLastDetections(), ports);
		}).subscribeOn(Schedulers.computation());
	}
}
