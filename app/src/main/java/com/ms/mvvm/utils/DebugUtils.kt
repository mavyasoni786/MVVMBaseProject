package com.ms.mvvm.utils

import android.widget.Toast
import com.ms.mvvm.BuildConfig
import com.ms.mvvm.MyApp

import io.reactivex.functions.Action

class DebugUtils {

    companion object {

        @Throws(Throwable::class)
        @JvmStatic
        fun verifyElseThrow(conditionToAssert: Boolean, errorFormatString: String, vararg args: Any) {
            if (!conditionToAssert) {
                val s = String.format(errorFormatString, *args)
                Trace.error(s)
                throw Throwable(s)
            }
        }

        @JvmStatic
        fun assertLog(conditionToAssert: Boolean, errorFormatString: String, vararg args: Any) {
            if (conditionToAssert) {
                return
            }

            val s = String.format(errorFormatString, *args)
            if (BuildConfig.DEBUG) {
                throw AssertionError(s)
            } else {
                Trace.error(s)
            }
        }

        @JvmStatic
        fun showToast(msg: String?, vararg args: Any) {
            assertLog(StringUtils.hasContent(msg), "cant have null message in toast")
            if (msg == null) {
                return
            }
            val s = String.format(msg, *args)
            showToast(s)
        }

        @JvmStatic
        fun showToast(msg: String) {
            if (!SystemUtils.isInstalledFromMarketPlace()) {
                if (ThreadUtils.isMainThread()) {
                    Toast.makeText(MyApp.instance.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } else {
                    TaskUtils.runAsyncOnMainThread(
                        "Show toast",
                        Action { Toast.makeText(MyApp.instance.applicationContext, msg, Toast.LENGTH_SHORT).show() })
                }
            }
        }

        @JvmStatic
        fun verifyNoInstances() {
            throw AssertionError("No instances.")
        }
    }

    init {
        verifyNoInstances()
    }
}
