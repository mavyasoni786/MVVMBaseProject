package com.ms.mvvm.splash

import androidx.databinding.DataBindingUtil
import com.ms.mvvm.R
import com.ms.mvvm.base.BaseActivity
import com.ms.mvvm.databinding.ActivitySplashBinding
import com.ms.mvvm.interfaces.IViewModel


class SplashActivity : BaseActivity() {

    private lateinit var mSplashViewModel: SplashViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun createViewModel(): IViewModel {
        mSplashViewModel = mViewModelFactory.createSplashViewModel(getActivityComponent())
        return mSplashViewModel
    }

    override fun onCreate() {
        val binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, getLayoutId())
        binding.viewModel = mSplashViewModel
    }

}
