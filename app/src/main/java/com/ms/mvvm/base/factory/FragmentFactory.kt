package com.ms.mvvm.base.factory

import android.os.Bundle
import com.ms.mvvm.home.home.HomeFragment
import com.ms.mvvm.interfaces.IFragmentFactory


class FragmentFactory : IFragmentFactory {
//
    override fun createHomeFragment(inputBundle: Bundle?): HomeFragment {
        val fragment = HomeFragment()
        if (inputBundle != null)
            fragment.arguments = inputBundle
        return fragment
    }


}