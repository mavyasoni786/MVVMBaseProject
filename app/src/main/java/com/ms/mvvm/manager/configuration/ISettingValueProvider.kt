package com.ms.mvvm.manager.configuration

interface ISettingValueProvider {

    /**
     * State of the setting from the particular providers source
     */
    enum class SettingState {
        /**
         * If this value is returned then the next provider will be queried for the setting state
         */
        UNKNOWN,
        /**
         * Setting is granted.
         *
         * This is a terminal state - When this is returned, the next provider will not be queried
         */
        GRANTED,
        /**
         * Setting is blocked.
         *
         * This is a terminal state - When this is returned, the next provider will not be queried
         */
        BLOCKED,
        /**
         * Current state is not known, we need to query the user and ask for permission.
         *
         * Only providers that return true for canRequestSettingPermission should return this
         * value. This should only be returned by the OS setting value provider.
         *
         * This is a terminal state - When this is returned, the next provider will not be queried
         */
        NEED_TO_REQUEST_FROM_USER
    }

    /**
     * @return the current state of the setting based on the data source that it represents
     */
    fun getSettingState(): SettingState

    /**
     * @return whether the user can grant the permission
     */
    fun canRequestSettingPermission(): Boolean

}