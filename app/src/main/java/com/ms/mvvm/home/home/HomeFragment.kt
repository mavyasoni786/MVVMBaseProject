package com.ms.mvvm.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ms.mvvm.R
import com.ms.mvvm.base.BaseFragment
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.IViewModel
import com.ms.mvvm.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment() {

    private lateinit var mHomeViewModel: HomeViewModel


    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun createViewModel(baseFragment: BaseFragment, activityComponent: IViewModelComponent): IViewModel {
        mHomeViewModel = mViewModelFactory.createHomeViewModel(activityComponent)
        return mHomeViewModel
    }

    override fun onFragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, getLayoutId(), container, false)
        binding.viewModel = mHomeViewModel
        return binding.root
    }
}