package com.ms.mvvm.manager.configuration

import com.ms.mvvm.interfaces.IBaseModule

interface IConfigurationManager : IBaseModule {

    /**
     * List of settings that the configuration manager supports
     */
    enum class Setting {
        MAX_SETTING
    }

    /**
     * returns the permissions for the capability
     * @param settingToQuery setting to return the capability for
     * @return if the permission is provided
     */
    abstract fun isSettingGranted(settingToQuery: Setting): Boolean

    /**
     * checks to see if this is a readonly setting or if we can request the user for permission.
     *
     * Only call requestPermission if this method returns true. requestPermission is guaranteed
     * to fail of this function returns false
     *
     * @param setting setting to query from the user
     * @return whether the permission can be requested from the user
     */
    abstract fun canRequestPermission(setting: Setting): Boolean

    /**
     * if permission for the particular setting can be requested, an async task is kicked
     * off that will ask the user for the permission. Upon user input an IConfigurationManagerEvent
     * event with eventType = PermissionStateChanged will be fired.
     *
     * All callers should query the configurationManager after receiving the IConfigurationManager
     * event
     *
     * Note: if canRequestPermission returns false this method is guaranteed to fail
     * @param settingToRequest setting to request permission for
     */
    abstract fun requestPermission(settingToRequest: Setting)

    /**
     * Activities that have received the permission event can callback on this method once
     * the user has input their choice.
     *
     * The osPermissionName and requestId should be the ones that were fired by the
     * IConfigurationManager in the IConfigurationManagerEvent with type = RequestUserForPermissions
     * @param osPermissionName permission name that was requested from the user.
     * @param requestID request id from IConfigurationManagerEvent
     * @param granted whether the user granted the permission
     */
    abstract fun onPermissionRequestResults(osPermissionName: String, requestID: Int, granted: Boolean)
}