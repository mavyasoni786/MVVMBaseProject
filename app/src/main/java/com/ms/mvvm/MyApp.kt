package com.ms.mvvm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.ms.mvvm.base.factory.*
import com.ms.mvvm.eventbus.EventBus
import com.ms.mvvm.injection.modules.AppModule
import com.ms.mvvm.interfaces.IApp
import com.ms.mvvm.interfaces.IAppLifecycleCallback
import com.ms.mvvm.interfaces.IPlatformLayerFactory
import com.ms.mvvm.utils.SystemUtils
import com.ms.mvvm.utils.Trace
import timber.log.Timber


class MyApp : Application(), IApp {

    companion object {
        lateinit var instance: MyApp

        //region static methods
        fun getInstance(): IApp {
            return instance
        }
    }

    lateinit var appModule: AppModule
    lateinit var mRCPlatform: IPlatformLayerFactory
    private lateinit var mLifecycleCallbacks: IAppLifecycleCallback


    override fun onCreate() {
        super.onCreate()

        mLifecycleCallbacks = AppLifecycleCallback()

        initializeRCApplicationEssentials(this)
        instance = this
        val eventBus = EventBus()
        appModule = AppModule(
            this,
            ComponentFactory(this),
            ViewModelFactory(),
            FragmentFactory(),
            ModuleLifecycleManager(),
            SharedPreferenceWrapper(this),
            eventBus
        )
        registerActivityLifecycleCallbacks(mLifecycleCallbacks);
        mRCPlatform = AppPlatformLayer(appModule)
    }

    override fun getApplicationContext(): ContextWrapper {
        return this
    }

    override fun getActivity(): Activity? {
        return mLifecycleCallbacks.getActivity()
    }

    override fun onTerminate() {
        super.onTerminate()
        Trace.logFunc()
        mRCPlatform.cleanup()
        appModule.cleanup()
        cleanupLogs()
    }

    private fun cleanupLogs() {
        Timber.uprootAll()
    }

    override fun getApplicationModule(): AppModule {
        return this.appModule
    }

    override fun initializeRCApplicationEssentials(c: Context) {

        SystemUtils.initializeSystemUtils(c.packageManager.getInstallerPackageName(c.packageName!!))


        if (!SystemUtils.isInstalledFromMarketPlace()) {
            Timber.plant(Timber.DebugTree())
        } else {
            //Fabric configuration
            //Timber.plant(Timber.DebugTree())
        }
    }

    override fun getPlatform(): IPlatformLayerFactory {
        return this.mRCPlatform
    }
}