package com.ms.mvvm.interfaces

interface ISharedPreferences {

    object Keys {
        val IS_LOGIN = "is_login"
        val X_ACCESS_TOKEN = "x-access-token"
        val X_USER_TOKEN = "x-user-token"
    }

    fun setString(key: String, value: String)

    fun getString(key: String, defaultValue: String): String

    fun setInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int): Int

    fun setBoolean(key: String, value: Boolean)

    fun getBoolean(key: String, defaultValue: Boolean): Boolean


    fun clearSharedPreference()


}
