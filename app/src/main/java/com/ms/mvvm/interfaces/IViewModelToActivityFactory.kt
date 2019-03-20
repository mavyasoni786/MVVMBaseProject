package com.ms.mvvm.interfaces

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ms.mvvm.base.BaseFragmentManager
import com.ms.mvvm.base.BaseFragmentState

interface IViewModelToActivityFactory {

    fun startActivity(activityClass: Class<out Activity>)

    fun startActivityWithBundle(activityClass: Class<out Activity>, bundle: Bundle)

    fun startActivityAndFinishThis(activityClass: Class<out Activity>)

    fun finish()

    fun onBackPressed()

    fun getActivity(): AppCompatActivity

    fun getSupportFragmentManager(): FragmentManager

    fun getFragmentManager(): FragmentManager

    fun getBaseFragmentManager(): BaseFragmentManager

    fun attachFragment(containerId: Int, isAddToBackStack: Boolean, baseFragment: Fragment)

    fun addFragment(mBaseFragmentState: BaseFragmentState, keys: Any?, isAnimation: Boolean = false)

    fun replaceFragment(mBaseFragmentState: BaseFragmentState, keys: Any?, isAnimation: Boolean = false)

    fun addFragment(containerId: Int, isAddToBackStack: Boolean, previousFragment: Fragment, fragment: Fragment)
}