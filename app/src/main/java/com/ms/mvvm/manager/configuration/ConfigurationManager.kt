package com.ms.mvvm.manager.configuration

import android.content.Context
import com.ms.mvvm.eventbus.IEventBus
import com.ms.mvvm.utils.Trace
import javax.inject.Inject

class ConfigurationManager @Inject constructor(var mEventBus: IEventBus, var applicationContext: Context) :
    IConfigurationManager {
    protected var mSettingDefinitions: SettingDefinitions

    init {
        mSettingDefinitions = SettingDefinitions(applicationContext)
    }

    override fun cleanupModule() {
        Trace.logFunc()
    }

    override fun isSettingGranted(settingToQuery: IConfigurationManager.Setting): Boolean {
        return mSettingDefinitions.getSettingAggregator(settingToQuery).getValue() === ISettingValueProvider.SettingState.GRANTED
    }

    override fun canRequestPermission(setting: IConfigurationManager.Setting): Boolean {
        return mSettingDefinitions.getSettingAggregator(setting).canRequestSettingPermission()
    }

    override fun requestPermission(settingToRequest: IConfigurationManager.Setting) {
        val canQuery = canRequestPermission(settingToRequest)
        Trace.info("invalid request for permissions " + canQuery)
        if (!canQuery) {
            Trace.error("requesting for permission which is not allowed")
            return
        }

        val currentState = mSettingDefinitions.getSettingAggregator(settingToRequest).getValue()

        if (currentState === ISettingValueProvider.SettingState.NEED_TO_REQUEST_FROM_USER) {
            mEventBus.send(
                ConfigurationManagerEvent(
                    IConfigurationManagerEvent.EventType.RequestUserForPermissions,
                    settingToRequest, generateRequest(settingToRequest))
            )
        }
    }

    private fun generateRequest(settingToRequest: IConfigurationManager.Setting): PermissionRequestParams {
        val permissionName = mSettingDefinitions.getAndroidPermissionName(settingToRequest)
        val permissionRationale = mSettingDefinitions.getAndroidPermissionRequestRationale(settingToRequest)
        return PermissionRequestParams(permissionName!!, permissionRationale, settingToRequest.ordinal)
    }

    override fun onPermissionRequestResults(osPermissionName: String, requestID: Int, granted: Boolean) {
        val setting = IConfigurationManager.Setting.values()[requestID]
        mEventBus.send(ConfigurationManagerEvent(IConfigurationManagerEvent.EventType.PermissionStateChanged, setting, null))
    }
}