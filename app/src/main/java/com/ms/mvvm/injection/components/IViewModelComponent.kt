package com.ms.mvvm.injection.components

import com.ms.mvvm.injection.scope.ActivityScope

import com.ms.mvvm.injection.modules.AppModule
import com.ms.mvvm.injection.modules.ActivityViewModelModule
import com.ms.mvvm.injection.modules.DialogManagerModule
import com.ms.mvvm.splash.SplashViewModel

import dagger.Component

@ActivityScope
@Component(
    dependencies = [IPlatformLifetimeComponent::class],
    modules = [(AppModule::class),
        (ActivityViewModelModule::class)]
)
interface IViewModelComponent {

    fun inject(model: SplashViewModel)

}