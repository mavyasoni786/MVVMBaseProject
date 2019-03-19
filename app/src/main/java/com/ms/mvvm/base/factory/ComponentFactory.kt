package com.ms.mvvm.base.factory

import com.ms.mvvm.injection.components.DaggerIViewModelComponent
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.injection.modules.ActivityViewModelModule
import com.ms.mvvm.interfaces.IApp
import com.ms.mvvm.interfaces.IComponentFactory


class ComponentFactory(var mApplication: IApp) : IComponentFactory {

    override fun createActivityViewModelComponent(module: ActivityViewModelModule): IViewModelComponent {
        return DaggerIViewModelComponent.builder()
                .appModule(mApplication.getApplicationModule())
                .iPlatformLifetimeComponent(mApplication.getPlatform().getApplicationLifetimeComponent())
                .activityViewModelModule(module)
                .build()
    }


}