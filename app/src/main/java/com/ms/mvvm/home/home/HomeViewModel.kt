package com.ms.mvvm.home.home

import com.ms.mvvm.base.BaseViewModel
import com.ms.mvvm.injection.components.IViewModelComponent

class HomeViewModel(iViewModelComponent: IViewModelComponent) : BaseViewModel(iViewModelComponent) {
    override fun injectMembers(activityComponent: IViewModelComponent) {
        activityComponent.inject(this)
    }
}