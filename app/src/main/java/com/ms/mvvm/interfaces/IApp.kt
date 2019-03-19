package com.ms.mvvm.interfaces

import android.content.Context
import com.ms.mvvm.injection.modules.AppModule
import android.app.Activity


interface IApp {

    /**
     * @return Application context that is needed for various utility methods
     * when interacting with Android
     */
    fun getApplicationContext(): android.content.ContextWrapper

    /**
     * @return Module used to build all dagger components
     */
    fun getApplicationModule(): AppModule

    fun initializeRCApplicationEssentials(c: Context)

    fun getPlatform(): IPlatformLayerFactory

    fun getActivity(): Activity?

}