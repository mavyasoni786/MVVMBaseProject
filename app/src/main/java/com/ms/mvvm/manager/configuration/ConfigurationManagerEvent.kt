package com.ms.mvvm.manager.configuration

import com.ms.mvvm.eventbus.CBaseEvent

class ConfigurationManagerEvent(var mEventType: IConfigurationManagerEvent.EventType,
                                var mSetting: IConfigurationManager.Setting,
                                var mRequestParams: IConfigurationManagerEvent.IPermissionRequestParams?) : CBaseEvent(),
    IConfigurationManagerEvent {


    override fun getEventType(): IConfigurationManagerEvent.EventType {
        return mEventType
    }

    override fun getPermissionRequestParams(): IConfigurationManagerEvent.IPermissionRequestParams {
        return mRequestParams!!
    }

    override fun getSetting(): IConfigurationManager.Setting {
        return mSetting
    }

}