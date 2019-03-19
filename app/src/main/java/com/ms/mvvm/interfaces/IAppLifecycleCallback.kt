package com.ms.mvvm.interfaces

import android.app.Activity
import android.app.Application


interface IAppLifecycleCallback : Application.ActivityLifecycleCallbacks {

    fun getActivity(): Activity?

}