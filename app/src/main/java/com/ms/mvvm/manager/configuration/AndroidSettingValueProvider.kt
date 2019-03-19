package com.ms.mvvm.manager.configuration

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

class AndroidSettingValueProvider(var mOSPermissionName: String,
                                  var mPermissionRequestRationale: Int,
                                  var mApplicationContext: Context) : ISettingValueProvider {


    override fun canRequestSettingPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    override fun getSettingState(): ISettingValueProvider.SettingState {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            ISettingValueProvider.SettingState.GRANTED
        } else {
            if (mApplicationContext.checkSelfPermission(mOSPermissionName) == PackageManager.PERMISSION_GRANTED) ISettingValueProvider.SettingState.GRANTED else ISettingValueProvider.SettingState.NEED_TO_REQUEST_FROM_USER
        }
    }

    fun getAndroidPermissionName(): String {
        return mOSPermissionName
    }

    fun getAndroidPermissionRequestRationale(): Int {
        return mPermissionRequestRationale
    }
}