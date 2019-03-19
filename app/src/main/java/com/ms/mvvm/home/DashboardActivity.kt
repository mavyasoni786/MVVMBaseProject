package com.ms.mvvm.home

import androidx.databinding.DataBindingUtil
import com.ms.mvvm.R
import com.ms.mvvm.base.BaseActivity
import com.ms.mvvm.databinding.ActivityMainBinding

import com.ms.mvvm.interfaces.IViewModel

class DashboardActivity : BaseActivity() {
    private lateinit var mDashboardViewModel: DashboardViewModel
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createViewModel(): IViewModel {
        mDashboardViewModel = mViewModelFactory.createDashboardViewModel(getActivityComponent())
        return mDashboardViewModel
    }

    override fun onCreate() {
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, getLayoutId())
        binding.viewModel = mDashboardViewModel
    }
}