package com.ms.mvvm.eventbus

import io.reactivex.disposables.Disposable


interface IEventBus {

    /**
     * Call this method to fire an event asynchronously. The event will be delivered
     * to all the listeners on the main thread
     * @param event event to fire
     */
    fun send(event: IBaseEvent)

    /**
     * Call this method to register an event listener. All the events will be delivered to the
     * event listener on the main thread.
     * @param eventBusListener event listener
     * @return Subscription that marks the registration.
     * To de-register an event listener, un-subscribe on the subscription returned.
     */
    fun subscribe(eventBusListener: IEventBusListener): Disposable
}