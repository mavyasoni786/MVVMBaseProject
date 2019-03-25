package com.ms.mvvm.webservice


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CustomCoroutineScope : LifecycleObserver, CoroutineScope {


    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
        job = Job()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy() {
        job.cancel() // Cancel job on activity destroy. After destroy all children jobs will be cancelled automatically
    }

    fun getCoroutineScope(): CoroutineScope {
        return this
    }

}