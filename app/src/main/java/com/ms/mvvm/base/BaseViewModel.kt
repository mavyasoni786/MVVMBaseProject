package com.ms.mvvm.base

import android.os.Bundle
import androidx.databinding.BaseObservable
import androidx.fragment.app.Fragment
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.IFragmentFactory
import com.ms.mvvm.interfaces.IViewModel
import com.ms.mvvm.interfaces.IViewModelFactory
import com.ms.mvvm.interfaces.IViewModelToActivityFactory
import com.ms.mvvm.utils.Trace
import javax.inject.Inject

abstract class BaseViewModel : BaseObservable, IViewModel {

    @Inject
    lateinit var mAttachedActivity: IViewModelToActivityFactory

    @Inject
    lateinit var mFragmentFactory: IFragmentFactory


    @Inject
    lateinit var mViewModelFactory: IViewModelFactory

    lateinit var fragment: Fragment

    abstract fun injectMembers(activityComponent: IViewModelComponent)

    constructor(iViewModelComponent: IViewModelComponent) {
        this.injectMembers(iViewModelComponent)
        createChildViewModels(activityComponent = iViewModelComponent)

    }

    open fun createChildViewModels(activityComponent: IViewModelComponent) {
        Trace.logFunc()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }
}