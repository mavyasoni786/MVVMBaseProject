package com.ms.mvvm.injection.modules

import com.ms.mvvm.base.BaseActivity
import com.ms.mvvm.base.factory.ViewModelToActivityFactory
import com.ms.mvvm.interfaces.IViewModelToActivityFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityViewModelModule {

    var mModelToActivityProxy: IViewModelToActivityFactory

    constructor(activity: BaseActivity) {
        this.mModelToActivityProxy = getNewProxy(activity)
    }

    @Provides
    fun provideActivityProxy(): IViewModelToActivityFactory {
        return mModelToActivityProxy
    }

    fun getNewProxy(activity: BaseActivity): IViewModelToActivityFactory {
        return ViewModelToActivityFactory(activity)
    }
}