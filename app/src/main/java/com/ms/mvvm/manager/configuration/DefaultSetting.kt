package com.ms.mvvm.manager.configuration

class DefaultSetting(var mDefaultState: ISettingValueProvider.SettingState) : ISettingValueProvider {

    override fun getSettingState(): ISettingValueProvider.SettingState {
        return mDefaultState
    }

    override fun canRequestSettingPermission(): Boolean {
        return false
    }
}