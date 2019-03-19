package com.ms.mvvm.injection.modules

import android.content.Context
import com.ms.mvvm.interfaces.*
import com.ms.mvvm.base.factory.ComponentFactory
import com.ms.mvvm.base.factory.ModuleLifecycleManager
import com.ms.mvvm.base.factory.SharedPreferenceWrapper
import dagger.Module
import dagger.Provides

@Module
class AppModule(var iApp: IApp,
                var mComponentFactory: ComponentFactory,
                var mViewModelFactory: IViewModelFactory,
                var mFragmentFactory: IFragmentFactory,
                var mLifecycleManager: ModuleLifecycleManager,
                var mSharedPreferenceWrapper: SharedPreferenceWrapper
) {

    @Provides
    fun getApplicationContext(): Context {
        return iApp.getApplicationContext()
    }

    @Provides
    fun getApplication(): IApp {
        return iApp
    }

    @Provides
    fun getViewModelFactory(): IViewModelFactory {
        return mViewModelFactory
    }

    @Provides
    fun getComponentFactory(): IComponentFactory {
        return mComponentFactory
    }

    @Provides
    fun getFragmentFactory(): IFragmentFactory {
        return mFragmentFactory
    }


    @Provides
    fun getLifecycleManager(): IModuleLifecycleManager {
        return mLifecycleManager
    }

    @Provides
    fun getSharedPreferences(): ISharedPreferences {
        return mSharedPreferenceWrapper
    }

    fun cleanup() {
        mLifecycleManager.cleanup()
    }
}