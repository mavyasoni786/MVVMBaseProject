package com.ms.mvvm.manager.configuration

interface IConfigurationManagerEvent {

    /**
     * Types of events that can be fired from the component implementing IConfigurationManager
     */
    enum class EventType {
        /**
         * Fired whenever a permission for a particular setting has changed. The value
         * can change if the user has reacted to a permission request or if there was a
         * change that was sent down from the service
         */
        PermissionStateChanged,

        /**
         * Fired when any component in the app requests for permissions. When this event is
         * fired an IPermissionRequestParams object will contain details on the setting
         * for which permissions are being requested
         *
         *
         * This event is solely meant for currently running activity that can request the user
         * for permissions
         */
        RequestUserForPermissions

    }

    /**
     * @return type of IConfigurationEvent that was fired by the component
     * implementing IConfigurationManager
     */
    fun getEventType(): EventType

    /**
     * This method will only have a valid return value when eventType = RequestUserForPermissions
     *
     * @return data about the setting for which the permissions need to be requested
     */
    fun getPermissionRequestParams(): IPermissionRequestParams

    /**
     * @return Setting for which this IConfigurationManagerEvent was fired.
     */
    fun getSetting(): IConfigurationManager.Setting

    /**
     * Contains details about the permission request for a particular setting.
     * See EventType.RequestUserForPermissions for details.
     */
    interface IPermissionRequestParams {

        /**
         * @return permission name that should be given to android when requesting the user for
         * the permission for a particular setting
         */
        fun getPermissionName(): String

        /**
         * @return the rationale that should be shown to the user when requesting for the permission
         * for a particular setting
         */
        fun getPermissionRationale(): Int

        /**
         * @return abstract handle that should be passed back to IConfigurationManager in
         * onPermissionRequestResults after the user has reacted to the user permission request
         */
        fun getRequestId(): Int
    }

}