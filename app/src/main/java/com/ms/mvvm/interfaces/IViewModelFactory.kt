package com.ms.mvvm.interfaces

import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.splash.SplashViewModel

interface IViewModelFactory {

    fun createSplashViewModel(iViewModelComponent: IViewModelComponent): SplashViewModel

}