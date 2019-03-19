package com.ms.mvvm.base.factory

import com.ms.mvvm.injection.components.IPlatformLifetimeComponent
import com.ms.mvvm.injection.modules.AppModule
import com.ms.mvvm.interfaces.IModuleLifecycleManager
import com.ms.mvvm.interfaces.IPlatformLayerFactory
import com.ms.mvvm.injection.components.DaggerIPlatformLifetimeComponent

class AppPlatformLayer(var appModule: AppModule) : IPlatformLayerFactory {

    var sRCPlatform: IPlatformLayerFactory
    var mPlatformLifetimeComponent: IPlatformLifetimeComponent

    init {
        sRCPlatform = this
        mPlatformLifetimeComponent = DaggerIPlatformLifetimeComponent.builder()
                .appModule(appModule)
                .build()
    }

    override fun getApplicationLifetimeComponent(): IPlatformLifetimeComponent {
        return mPlatformLifetimeComponent
    }

    override fun cleanup() {
        appModule.getLifecycleManager().resetAndRemoveModules(IModuleLifecycleManager.APPLICATION_LIFECYCLE_COMPONENT)
    }
}