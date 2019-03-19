package com.ms.mvvm.interfaces

import com.ms.mvvm.interfaces.IBaseModule


interface IModuleLifecycleManager {

    fun registerModule(module: IBaseModule, lifecycleType: Int)

    fun resetAndRemoveModules(lifecycleType: Int)

    companion object {

        val APPLICATION_LIFECYCLE_COMPONENT = 0
        val ACTIVITY_LIFECYCLE_COMPONENT = 1
        val MAX_LIFECYCLE_LEVEL = 2
    }

}
