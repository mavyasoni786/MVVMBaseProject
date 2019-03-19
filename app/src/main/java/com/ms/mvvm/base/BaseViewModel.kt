package com.ms.mvvm.base

import androidx.databinding.BaseObservable
import android.os.Bundle

import androidx.fragment.app.Fragment
import com.ms.mvvm.eventbus.IBaseEvent
import com.ms.mvvm.eventbus.IEventBus
import com.ms.mvvm.eventbus.IEventBusListener
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.IFragmentFactory
import com.ms.mvvm.interfaces.IViewModel
import com.ms.mvvm.interfaces.IViewModelFactory
import com.ms.mvvm.interfaces.IViewModelToActivityFactory
import com.ms.mvvm.manager.configuration.ConfigurationManager
import com.ms.mvvm.utils.Trace
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel : BaseObservable, IEventBusListener, IViewModel {

    @Inject
    lateinit var mAttachedActivity: IViewModelToActivityFactory

    @Inject
    lateinit var mFragmentFactory: IFragmentFactory

    @Inject
    lateinit var mEventBus: IEventBus

    @Inject
    lateinit var mConfigurationManager: ConfigurationManager

    @Inject
    lateinit var mViewModelFactory: IViewModelFactory

    protected var mSubscription: CompositeDisposable

    lateinit var fragment: Fragment

    abstract fun injectMembers(activityComponent: IViewModelComponent)

    constructor(iViewModelComponent: IViewModelComponent) {
        this.injectMembers(iViewModelComponent)
        mSubscription = CompositeDisposable()
        mSubscription.add(mEventBus.subscribe(this))
        createChildViewModels(activityComponent = iViewModelComponent)

    }

    override fun onEvent(baseEvent: IBaseEvent) {

    }

    open fun createChildViewModels(activityComponent: IViewModelComponent) {
        Trace.logFunc()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

    }

    override fun onStart() {

    }

    override fun onResume() {
        mSubscription.add(mEventBus.subscribe(this))
    }

    override fun onPause() {
        mSubscription.clear()
    }

    override fun onStop() {
        mSubscription.clear()
    }

    override fun onDestroy() {
        mSubscription.clear()
        mSubscription.dispose()
    }
}