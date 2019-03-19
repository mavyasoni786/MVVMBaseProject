package com.ms.mvvm.base.factory

import android.app.Activity
import android.os.Bundle
import com.ms.mvvm.interfaces.IAppLifecycleCallback


class AppLifecycleCallback : IAppLifecycleCallback {

    private var activity: Activity? = null

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        this.activity = activity
    }

    override fun onActivityStarted(activity: Activity?) {
        this.activity = activity
    }

    override fun onActivityResumed(activity: Activity?) {
        this.activity = activity
    }

    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityStopped(activity: Activity?) {
        if (this.activity === activity) {
            this.activity = null
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
        this.activity = activity
    }

    override fun onActivityDestroyed(activity: Activity?) {
        if (this.activity === activity) {
            this.activity = null
        }
    }

    override fun getActivity(): Activity? {
        return activity
    }
}