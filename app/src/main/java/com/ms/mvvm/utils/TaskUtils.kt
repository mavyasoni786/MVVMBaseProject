package com.ms.mvvm.utils

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action

class TaskUtils {

    companion object {
        @JvmStatic
        fun runAsyncOnMainThread(taskName: String, actionToExecute: Action): Disposable {
            return Observable.create(ObservableOnSubscribe<Action> { actionToExecute.run() }).subscribeOn(AndroidSchedulers.mainThread()).subscribe()
        }
    }


}
