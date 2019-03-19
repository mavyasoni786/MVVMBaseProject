package com.ms.mvvm.eventbus

interface IBaseEvent {
    fun isEventOfPassedType(eventType: Class<*>): Boolean
}