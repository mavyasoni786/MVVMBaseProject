//package com.ms.mvvm.injection.modules
//
//import com.ms.mvvm.interfaces.IModuleLifecycleManager
//import com.ms.mvvm.interfaces.IWebClient
//import com.mparking.webclient.WebClient
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//@Singleton
//@Module
//class WebClientModule {
//
//    @Singleton
//    @Provides
//    fun getWebClient(component: WebClient, lifecycleManager: IModuleLifecycleManager): IWebClient {
//        lifecycleManager.registerModule(component, IModuleLifecycleManager.APPLICATION_LIFECYCLE_COMPONENT)
//        return component
//    }
//}