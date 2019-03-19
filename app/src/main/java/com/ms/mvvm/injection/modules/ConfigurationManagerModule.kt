package com.ms.mvvm.injection.modules

import com.ms.mvvm.interfaces.IModuleLifecycleManager
import com.ms.mvvm.manager.configuration.ConfigurationManager
import com.ms.mvvm.manager.configuration.IConfigurationManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class ConfigurationManagerModule {
    @Singleton
    @Provides
    internal fun getConfigurationManager(component: ConfigurationManager, lifecycleManager: IModuleLifecycleManager): IConfigurationManager {
        lifecycleManager.registerModule(component, IModuleLifecycleManager.APPLICATION_LIFECYCLE_COMPONENT)
        return component
    }

}