package com.ms.mvvm.base

import com.ms.mvvm.home.home.HomeFragment

enum class BaseFragmentState(var fragment: Class<out BaseFragment>) {

    HOME(HomeFragment::class.java);

    companion object {

        // To get BaseFragmentState  enum from class name
        fun getValue(value: Class<*>): BaseFragmentState {
            return BaseFragmentState.values().firstOrNull { it.fragment == value }
                    ?: HOME// not found
        }
    }

}
