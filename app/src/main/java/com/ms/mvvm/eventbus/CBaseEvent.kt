package com.ms.mvvm.eventbus

abstract class CBaseEvent : IBaseEvent {

    override fun isEventOfPassedType(eventType: Class<*>): Boolean {
        return eventType.isInstance(this)
    }
}