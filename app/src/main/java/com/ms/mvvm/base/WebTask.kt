//package com.ms.mvvm.base
//
//import androidx.annotation.CallSuper
//import com.mparking.eventbus.IBaseEvent
//import com.mparking.eventbus.IEventBus
//import com.mparking.eventbus.IEventBusListener
//import com.mparking.interfaces.IWebClient
//import com.mparking.interfaces.IWebTaskCallback
//import com.mparking.manager.dialog.IDialogManager
//import com.mparking.utils.Trace
//import com.mparking.webclient.ExponentialBackOffRetryStrategy
//import io.reactivex.Observable
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.functions.Consumer
//import io.reactivex.schedulers.Schedulers
//import retrofit2.Response
//import retrofit2.adapter.rxjava2.Result
//
//abstract class WebTask<T>(
//    protected val mDialogManager: IDialogManager,
//    protected val mEventBus: IEventBus,
//    protected var mCallback: IWebTaskCallback<T>?,
//    protected val webClient: IWebClient
//) : IEventBusListener {
//
//    protected var mCompositeSubscription: CompositeDisposable? = CompositeDisposable()
//    private lateinit var mApiCall: Observable<Result<T>>
//    private var mOnSuccessfulApiResponse: Consumer<Response<T>>? = null
//    protected var mNullResponseAllowed = false
//
//    @CallSuper
//    fun cleanUp() {
//        Trace.logFunc()
//        if (this.mCompositeSubscription == null) {
//            Trace.error("Re-entrant call")
//            return
//        }
//        if (!this.mCompositeSubscription!!.isDisposed) {
//            this.mCompositeSubscription!!.dispose()
//        }
//
//        mDialogManager.dismissProgressDialog()
//        this.mCompositeSubscription = null
//        this.mCallback = null
//    }
//
//    @CallSuper
//    fun cancel() {
//        Trace.logFunc()
//        this.cleanUp()
//    }
//
//    override fun onEvent(baseEvent: IBaseEvent) {
//
//    }
//
//    //
//    fun startWebTask(apiCall: Observable<Result<T>>, onSuccessfulApiResponse: Consumer<Response<T>>) {
//        mDialogManager.showProgressDialog()
//        this.mApiCall = apiCall
//        this.mOnSuccessfulApiResponse = onSuccessfulApiResponse
//        startWebCall()
//    }
//
//    fun startWebTaskWithoutDialog(apiCall: Observable<Result<T>>, onSuccessfulApiResponse: Consumer<Response<T>>) {
//        this.mApiCall = apiCall
//        this.mOnSuccessfulApiResponse = onSuccessfulApiResponse
//        startWebCall()
//    }
//
//    private fun startWebCall() {
//        val onSuccessfulApiResponse: Consumer<Response<T>> = this.mOnSuccessfulApiResponse!!
//        mCompositeSubscription!!.add(
//            mApiCall.compose(ExponentialBackOffRetryStrategy<T>())
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    {
//                        onSuccessfulApiResponse.apply { accept(it) }
//                    },
//                    {
//                        onApiError(it)
//                    },
//                    {
//                        onWebTaskCompleted()
//                    })
//        )
//
//
//    }
//
//    protected fun onWebTaskCompleted() {
//        Trace.logFunc()
//        cleanUp()
//    }
//
//    protected fun onFailureApiResponse(response: Response<T>) {
//        mDialogManager.dismissProgressDialog()
//        if (!response.isSuccessful) {
//            Trace.info(response.message())
//            val message = response.message()
//
//            mCallback!!.onTaskFailed(message)
//        }
//    }
//
//    protected fun onApiError(t: Throwable) {
//        Trace.throwable(t)
//        val failure = t.message!!
//
//        mCallback!!.onTaskFailed(failure)
//        cleanUp()
//    }
//
//    protected fun dispatchResultToCallbackAndCleanup(apiResponse: Response<T>) {
//
//        if (!apiResponse.isSuccessful || apiResponse.body() == null && !mNullResponseAllowed) {
//            onFailureApiResponse(apiResponse)
//        } else {
//            mDialogManager.dismissProgressDialog()
//            val responseBody = apiResponse.body()
//            Trace.info(responseBody.toString())
//            mCallback!!.onTaskComplete(responseBody!!)
//        }
//    }
//
//    private fun error() {
//
//    }
//}
