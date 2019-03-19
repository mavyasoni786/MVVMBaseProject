package com.ms.mvvm.injection.components

import com.ms.mvvm.base.BaseActivity
import com.ms.mvvm.base.BaseDialogFragment
import com.ms.mvvm.base.BaseFragment
import com.ms.mvvm.base.factory.PlatformObjectsThatNeedAppWideLifetime
import com.ms.mvvm.injection.modules.AppModule
import com.ms.mvvm.injection.modules.ConfigurationManagerModule
import com.ms.mvvm.injection.modules.DialogManagerModule
//import com.ms.mvvm.injection.modules.WebClientModule
//import com.ms.mvvm.interfaces.IWebClient
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
//        WebClientModule::class,
        ConfigurationManagerModule::class,
        DialogManagerModule::class]
)
interface IPlatformLifetimeComponent {

//    fun getConfigurationManager(): IConfigurationManager
//    fun getDialogManager(): IDialogManager

//    fun getWebClient(): IWebClient

    fun inject(baseActivity: BaseActivity)

    fun inject(baseFragment: BaseFragment)

    fun inject(baseFragment: BaseDialogFragment)

    fun inject(obj: PlatformObjectsThatNeedAppWideLifetime)
}