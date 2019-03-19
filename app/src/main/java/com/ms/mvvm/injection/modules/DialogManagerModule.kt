package com.ms.mvvm.injection.modules

import com.ms.mvvm.interfaces.IModuleLifecycleManager
import com.ms.mvvm.manager.dialog.DialogManager
import com.ms.mvvm.manager.dialog.IDialogManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class DialogManagerModule {
    @Singleton
    @Provides
    internal fun getUserManager(component: DialogManager, lifecycleManager: IModuleLifecycleManager): IDialogManager {
        lifecycleManager.registerModule(component, IModuleLifecycleManager.APPLICATION_LIFECYCLE_COMPONENT)
        return component
    }

}