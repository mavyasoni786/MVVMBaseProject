package com.ms.mvvm.manager.configuration

final class SettingAggregator(var mValueProviders: Array<ISettingValueProvider>) {

    fun getValue(): ISettingValueProvider.SettingState {
        for (provider in mValueProviders) {
            val state = provider.getSettingState()
            if (state != ISettingValueProvider.SettingState.UNKNOWN) {
                return state
            }
        }
        return ISettingValueProvider.SettingState.UNKNOWN
    }

    fun canRequestSettingPermission(): Boolean {
        for (provider in mValueProviders) {
            if (provider.canRequestSettingPermission()) {
                return true
            }
        }
        return false
    }
}