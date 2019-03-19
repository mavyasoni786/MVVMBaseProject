package com.ms.mvvm.splash

import android.os.Handler
import android.view.View
import com.ms.mvvm.base.BaseViewModel
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.ISharedPreferences
import com.ms.mvvm.manager.dialog.IDialogManager
import com.ms.mvvm.utils.Trace
import javax.inject.Inject


class SplashViewModel(iViewModelComponent: IViewModelComponent) : BaseViewModel(iViewModelComponent) {

    @Inject
    lateinit var mSharedPreferenceWrapper: ISharedPreferences

    @Inject
    lateinit var mDialogManager:IDialogManager


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

    fun onClickShowProgress(view : View){
        mDialogManager.showProgressDialog()
        Handler().postDelayed({
            mDialogManager.dismissProgressDialog()
        },5000)

    }



}