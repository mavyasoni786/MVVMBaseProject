package com.ms.mvvm.eventbus

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class EventBus @Inject constructor() : IEventBus {

    private var _bus: PublishSubject<IBaseEvent> = PublishSubject.create<IBaseEvent>()

    override fun send(event: IBaseEvent) {
        _bus.onNext(event)
    }

    public fun cleanup() {
        _bus.onComplete()
    }

    public fun toObservable(): Observable<IBaseEvent> {
        return _bus
    }

    fun hasObservers(): Boolean {
        return _bus.hasObservers()
    }

    override fun subscribe(eventBusListener: IEventBusListener): Disposable {
        return toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { baseEvent ->
                    eventBusListener.onEvent(baseEvent)
                }
    }

}