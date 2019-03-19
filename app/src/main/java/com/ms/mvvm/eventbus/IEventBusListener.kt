package com.ms.mvvm.eventbus

interface IEventBusListener {
    fun onEvent(baseEvent: IBaseEvent)
}