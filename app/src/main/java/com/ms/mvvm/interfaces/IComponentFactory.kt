package com.ms.mvvm.interfaces

import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.injection.modules.ActivityViewModelModule


interface IComponentFactory {

    fun createActivityViewModelComponent(module: ActivityViewModelModule): IViewModelComponent

}