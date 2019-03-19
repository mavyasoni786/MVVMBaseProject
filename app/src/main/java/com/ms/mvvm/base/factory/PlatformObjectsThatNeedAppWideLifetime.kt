package com.ms.mvvm.base.factory

import com.ms.mvvm.injection.components.IPlatformLifetimeComponent


class PlatformObjectsThatNeedAppWideLifetime {

    fun inject(component: IPlatformLifetimeComponent) {
        component.inject(this)
    }
}