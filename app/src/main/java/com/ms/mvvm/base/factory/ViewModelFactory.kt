package com.ms.mvvm.base.factory

import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.IViewModelFactory
import com.ms.mvvm.splash.SplashViewModel

class ViewModelFactory : IViewModelFactory {

    override fun createSplashViewModel(iViewModelComponent: IViewModelComponent): SplashViewModel {
        return SplashViewModel(iViewModelComponent)
    }


}