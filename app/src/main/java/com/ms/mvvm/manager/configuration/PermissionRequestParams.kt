package com.ms.mvvm.manager.configuration

class PermissionRequestParams(var mPermissionName: String,
                              var mPermissionRationale: Int,
                              var mRequestId: Int) : IConfigurationManagerEvent.IPermissionRequestParams {


    override fun getPermissionName(): String {
        return mPermissionName
    }

    override fun getPermissionRationale(): Int {
        return mPermissionRationale
    }

    override fun getRequestId(): Int {
        return mRequestId
    }


}