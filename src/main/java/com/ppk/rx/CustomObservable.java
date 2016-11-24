package com.ppk.rx;

import org.springframework.stereotype.Component;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.concurrent.Callable;

@Component
public class CustomObservable {
	public <T> Observable<T> getObservable(final Callable<T> c, Scheduler scheduler) {
		Observable<T> observable = Observable.create(new Observable.OnSubscribe<T>() {

			@Override
			public void call(Subscriber<? super T> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						T result = c.call();
						observer.onNext(result);
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}

		});
		return observable.subscribeOn(scheduler);
	}

	public <T> Observable<T> getObservable(final Callable<T> c) {
		return getObservable(c, Schedulers.io());
	}
}