package com.ms.mvvm.manager.dialog

import com.ms.mvvm.interfaces.IBaseModule

interface IDialogManager : IBaseModule {

    fun showProgressDialog()

    fun dismissProgressDialog()

    fun isDialogShowing(): Boolean
}