package com.ms.mvvm.interfaces

interface IWebTaskCallback<T> {

    /*
    Invoked once the web task has been completed
     */
    fun onTaskComplete(response: T)

    /*
    Invoked when the task cant proceed further
     */
    fun onTaskFailed(message: String)
}