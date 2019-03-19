package com.ms.mvvm.base

import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.IViewModelToActivityFactory
import javax.inject.Inject

abstract class BaseRecyclerViewItemViewModel(activityComponent: IViewModelComponent) {

    @Inject
    lateinit var mAttachedActivity: IViewModelToActivityFactory

    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun injectMembers(activityComponent: IViewModelComponent)

    init {
        this.injectMembers(activityComponent)
    }
}