package com.ms.mvvm.base.factory


import com.ms.mvvm.interfaces.IBaseModule
import com.ms.mvvm.interfaces.IModuleLifecycleManager
import com.ms.mvvm.utils.Trace
import java.util.*
import javax.inject.Inject

class ModuleLifecycleManager @Inject
constructor() : IModuleLifecycleManager {

    private var mLifeCycleLevels: Array<LifecycleLevel?>? = null

    init {
        mLifeCycleLevels = Array(IModuleLifecycleManager.MAX_LIFECYCLE_LEVEL, { null })
        mLifeCycleLevels!![IModuleLifecycleManager.APPLICATION_LIFECYCLE_COMPONENT] = LifecycleLevel()
        mLifeCycleLevels!![IModuleLifecycleManager.ACTIVITY_LIFECYCLE_COMPONENT] = LifecycleLevel()
    }

    fun cleanup() {
        Trace.logFunc()
        for (i in mLifeCycleLevels!!.indices) {
            if (!mLifeCycleLevels!![i]!!.hasRegisteredModules()) {
                throw IllegalStateException("modules haven't been cleaned up at level = " + i)
            }
            mLifeCycleLevels!![i] = null
        }
        mLifeCycleLevels = null
    }

    override fun registerModule(module: IBaseModule, lifecycleType: Int) {
        Trace.logFuncWithMessage("module name = %s, lifecycleType = %s",
                module.javaClass.canonicalName, lifeCycleLevelToString(lifecycleType))
        verifyLifecycleIsWithinLimits(lifecycleType)
        mLifeCycleLevels!![lifecycleType]!!.registerModule(module)
    }

    override fun resetAndRemoveModules(lifecycleType: Int) {
        Trace.logFuncWithMessage("lifecycleType = %s", lifeCycleLevelToString(lifecycleType))
        verifyLifecycleIsWithinLimits(lifecycleType)
        mLifeCycleLevels!![lifecycleType]!!.resetAndRemoveModules()
    }

    private fun verifyLifecycleIsWithinLimits(lifecycleType: Int) {
        if (lifecycleType < 0 || lifecycleType > IModuleLifecycleManager.MAX_LIFECYCLE_LEVEL) {
            throw IllegalArgumentException("invalid lifecycle type = " + lifecycleType)
        }
    }

    private class LifecycleLevel {
        private val mModulesAtLevel = ArrayList<IBaseModule>()

        internal fun registerModule(m: IBaseModule) {
            if (mModulesAtLevel.contains(m)) {
                throw IllegalStateException("module already exists")
            }
            mModulesAtLevel.add(m)
        }

        internal fun resetAndRemoveModules() {
            for (m in mModulesAtLevel) {
                Trace.info("cleaning up module = %s", m.javaClass.canonicalName.toString())
                m.cleanupModule()
            }
            mModulesAtLevel.clear()
        }

        internal fun hasRegisteredModules(): Boolean {
            return !mModulesAtLevel.isEmpty()
        }
    }

    private fun lifeCycleLevelToString(lifeCycleLevel: Int): String {
        when (lifeCycleLevel) {
            IModuleLifecycleManager.APPLICATION_LIFECYCLE_COMPONENT -> return "APPLICATION_LIFECYCLE_COMPONENT"
            IModuleLifecycleManager.ACTIVITY_LIFECYCLE_COMPONENT -> return "ACTIVITY_LIFECYCLE_COMPONENT"
            IModuleLifecycleManager.MAX_LIFECYCLE_LEVEL -> return "MAX_LIFECYCLE_LEVEL"
            else -> throw IllegalArgumentException("Not valid lifecycle level, level provided = " + lifeCycleLevel)
        }
    }
}
