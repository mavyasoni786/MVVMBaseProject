package com.ms.mvvm.base.factory

import com.ms.mvvm.home.DashboardViewModel
import com.ms.mvvm.home.home.HomeViewModel
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.IViewModelFactory
import com.ms.mvvm.splash.SplashViewModel


class ViewModelFactory : IViewModelFactory {
    override fun createDashboardViewModel(iViewModelComponent: IViewModelComponent): DashboardViewModel {
        return DashboardViewModel(iViewModelComponent)
    }

    override fun createHomeViewModel(iViewModelComponent: IViewModelComponent): HomeViewModel {
        return HomeViewModel(iViewModelComponent)
    }

    override fun createSplashViewModel(iViewModelComponent: IViewModelComponent): SplashViewModel {
        return SplashViewModel(iViewModelComponent)
    }


}