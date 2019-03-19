package com.ms.mvvm.manager.configuration

import android.content.Context
import com.ms.mvvm.utils.Trace
import java.util.*
import kotlin.collections.ArrayList

final class SettingDefinitions(applicationContext: Context) {

    private var mAndroidSettingFactory: AndroidSettingFactory
    private lateinit var mSettings: ArrayList<SettingAggregator>

    private fun initializeSettings() {
        mSettings = ArrayList<SettingAggregator>()

        mSettings.add(
            IConfigurationManager.Setting.MAX_SETTING.ordinal,
                SettingAggregator(arrayOf(DefaultSetting(ISettingValueProvider.SettingState.BLOCKED)))
        )
    }

    private class AndroidSettingFactory internal constructor(private val mApplicationContext: Context) {
        private val mAndroidSettings: Dictionary<IConfigurationManager.Setting, AndroidSettingValueProvider>

        init {
            mAndroidSettings = Hashtable()
        }

        fun create(s: IConfigurationManager.Setting, osPermissionName: String, requestRationale: Int): AndroidSettingValueProvider {
            val provider = AndroidSettingValueProvider(osPermissionName, requestRationale, mApplicationContext)
            mAndroidSettings.put(s, provider)
            return provider
        }

        fun getAndroidPermissionName(s: IConfigurationManager.Setting): String? {
            val provider = mAndroidSettings.get(s)
            return provider?.getAndroidPermissionName()
        }

        fun getAndroidPermissionRequestRationale(s: IConfigurationManager.Setting): Int {
            val provider = mAndroidSettings.get(s)
            return if (provider != null) {
                provider.getAndroidPermissionRequestRationale()
            } else -1
        }
    }

    fun getSettingAggregator(s: IConfigurationManager.Setting): SettingAggregator {
        return mSettings[s.ordinal]
    }

    fun getAndroidPermissionName(s: IConfigurationManager.Setting): String? {
        return mAndroidSettingFactory.getAndroidPermissionName(s)
    }

    fun getAndroidPermissionRequestRationale(s: IConfigurationManager.Setting): Int {
        return mAndroidSettingFactory.getAndroidPermissionRequestRationale(s)
    }

    init {
        mAndroidSettingFactory = AndroidSettingFactory(applicationContext)
        initializeSettings()
        val numSettings = mSettings.size
        Trace.info("Number of settings registered = %d", numSettings)
        if (numSettings != IConfigurationManager.Setting.MAX_SETTING.ordinal + 1) {
            throw IllegalStateException("Incorrect number of settings")
        }
    }
}