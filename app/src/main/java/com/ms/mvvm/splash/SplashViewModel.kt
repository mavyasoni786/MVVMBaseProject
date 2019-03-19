package com.ms.mvvm.splash

import com.ms.mvvm.base.BaseViewModel
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.ISharedPreferences
import com.ms.mvvm.utils.Trace
import javax.inject.Inject


class SplashViewModel(iViewModelComponent: IViewModelComponent) : BaseViewModel(iViewModelComponent) {

    @Inject
    lateinit var mSharedPreferenceWrapper: ISharedPreferences


    override fun injectMembers(activityComponent: IViewModelComponent) {
        activityComponent.inject(this)
    }

    init {

    }


    override fun onResume() {
        super.onResume()
        Trace.logFunc()
    }

    override fun onPause() {
        super.onPause()
        Trace.logFunc()
    }


}