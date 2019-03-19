package com.ms.mvvm.interfaces

import com.ms.mvvm.home.DashboardViewModel
import com.ms.mvvm.home.home.HomeViewModel
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.splash.SplashViewModel

interface IViewModelFactory {

    fun createSplashViewModel(iViewModelComponent: IViewModelComponent): SplashViewModel
    fun createDashboardViewModel(iViewModelComponent: IViewModelComponent): DashboardViewModel
    fun createHomeViewModel(iViewModelComponent: IViewModelComponent): HomeViewModel

}