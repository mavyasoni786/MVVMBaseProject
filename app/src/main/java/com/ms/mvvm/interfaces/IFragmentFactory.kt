package com.ms.mvvm.interfaces

import android.os.Bundle
import com.ms.mvvm.home.home.HomeFragment

interface IFragmentFactory {
    fun createHomeFragment(inputBundle: Bundle?): HomeFragment

}