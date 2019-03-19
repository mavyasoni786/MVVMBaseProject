package com.ms.mvvm.interfaces

import android.os.Bundle

interface IViewModel {

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()
}