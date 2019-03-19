package com.ms.mvvm.interfaces

import com.ms.mvvm.injection.components.IPlatformLifetimeComponent

interface IPlatformLayerFactory {

    fun getApplicationLifetimeComponent(): IPlatformLifetimeComponent

    fun cleanup()
}