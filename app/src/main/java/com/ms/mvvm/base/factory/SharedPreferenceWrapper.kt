package com.ms.mvvm.base.factory

import android.content.Context
import android.content.SharedPreferences
import com.ms.mvvm.interfaces.IApp
import com.ms.mvvm.interfaces.ISharedPreferences
import com.ms.mvvm.utils.ThreadUtils


class SharedPreferenceWrapper(application: IApp) : ISharedPreferences {


    private val mSharedPreferences: SharedPreferences
    private val APPLICATION_SHARED_PREF_NAME = "APP_SHARED_PREFS"

    init {
        mSharedPreferences = application.getApplicationContext().getSharedPreferences(APPLICATION_SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun setString(key: String, value: String) {
        ThreadUtils.verifyInMainThreadOnDevice("SharedPreferenceWrapper.setString")
        mSharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return mSharedPreferences.getString(key, defaultValue)!!
    }

    override fun setInt(key: String, value: Int) {
        ThreadUtils.verifyInMainThreadOnDevice("SharedPreferenceWrapper.setInt")
        mSharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        ThreadUtils.verifyInMainThreadOnDevice("SharedPreferenceWrapper.setBoolean")
        mSharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    override fun clearSharedPreference() {
        mSharedPreferences.edit().clear().apply()
    }

}
