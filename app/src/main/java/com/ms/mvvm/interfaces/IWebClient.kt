package com.ms.mvvm.interfaces

interface IWebClient : IBaseModule {

    fun <T> createWebServiceProxy(service: Class<T>): T

    fun initRestClient()

    fun setAllowToCheck(isAllow: Boolean)
}