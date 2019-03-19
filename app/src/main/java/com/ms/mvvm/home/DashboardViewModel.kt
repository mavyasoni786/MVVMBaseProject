package com.ms.mvvm.home

import com.ms.mvvm.base.BaseViewModel
import com.ms.mvvm.injection.components.IViewModelComponent

class DashboardViewModel(iViewModelComponent: IViewModelComponent) : BaseViewModel(iViewModelComponent) {

    override fun injectMembers(activityComponent: IViewModelComponent) {
        activityComponent.inject(this)
    }

    init {
        mAttachedActivity
    }
}