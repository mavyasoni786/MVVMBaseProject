package com.ms.mvvm.interfaces

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface IViewModelToActivityFactory {

    fun startActivity(activityClass: Class<out Activity>)

    fun startActivityWithBundle(activityClass: Class<out Activity>, bundle: Bundle)

    fun startActivityAndFinishThis(activityClass: Class<out Activity>)

    fun finish()

    fun onBackPressed()

    fun getActivity(): AppCompatActivity

    fun getSupportFragmentManager(): FragmentManager

    fun getFragmentManager(): FragmentManager

    fun attachFragment(containerId: Int, isAddToBackStack: Boolean, baseFragment: Fragment)

    fun addFragment(containerId: Int, isAddToBackStack: Boolean, previousFragment: Fragment, fragment: Fragment)
}