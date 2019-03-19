package com.ms.mvvm.injection.components

import com.ms.mvvm.base.BaseActivity
import com.ms.mvvm.base.BaseDialogFragment
import com.ms.mvvm.base.BaseFragment
import com.ms.mvvm.base.factory.PlatformObjectsThatNeedAppWideLifetime
import com.ms.mvvm.injection.modules.AppModule
import com.ms.mvvm.injection.modules.DialogManagerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        DialogManagerModule::class]
)
interface IPlatformLifetimeComponent {


//    fun getDialogManager(): IDialogManager

    fun inject(baseActivity: BaseActivity)

    fun inject(baseFragment: BaseFragment)

    fun inject(baseFragment: BaseDialogFragment)

    fun inject(obj: PlatformObjectsThatNeedAppWideLifetime)
}