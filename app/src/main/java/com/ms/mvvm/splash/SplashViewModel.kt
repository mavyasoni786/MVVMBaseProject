package com.ms.mvvm.splash

import com.ms.mvvm.base.BaseViewModel
import com.ms.mvvm.injection.components.IViewModelComponent
import com.ms.mvvm.interfaces.ISharedPreferences
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashViewModel(iViewModelComponent: IViewModelComponent) : BaseViewModel(iViewModelComponent) {

    @Inject
    lateinit var mSharedPreferenceWrapper: ISharedPreferences


    override fun injectMembers(activityComponent: IViewModelComponent) {
        activityComponent.inject(this)
    }

    init {
        mSubscription.add(
            Completable.complete()
                .delay(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .doOnComplete {
                    if (mSharedPreferenceWrapper.getBoolean(ISharedPreferences.Keys.IS_LOGIN, false)) {
                        //                        mAttachedActivity.startActivityAndFinishThis(HomeActivity::class.java)
                    } else {
                        //                        mAttachedActivity.startActivityAndFinishThis(LoginActivity::class.java)
                    }
                }

                .subscribe()
        )

    }










}