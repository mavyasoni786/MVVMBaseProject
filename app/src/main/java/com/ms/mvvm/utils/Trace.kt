package com.ms.mvvm.utils

import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ms.mvvm.MyApp
import com.ms.mvvm.R
import com.ms.mvvm.interfaces.IDialogClickListener
import timber.log.Timber

class Trace {

    companion object {

        @JvmStatic
        fun verbose(message: String, vararg args: Any) {
            Timber.v(message, *args)
        }

        @JvmStatic
        fun info(message: String, vararg args: Any) {
            Timber.i(message, *args)
        }

        @JvmStatic
        fun warn(message: String, vararg args: Any) {
            Timber.w(message, *args)
        }

        /**
         * Show toast
         *
         * @param context Application/Activity context
         * @param message Message which is display in toast.
         */
        @JvmStatic
        fun toast(message: String) {
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(MyApp.instance.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * Show Default dialog.
         *
         * @param context Application/Activity Context for creating dialog.
         * @param title   Title of dialog
         * @param message Message of dialog
         */
        @JvmStatic
        fun dialog(title: String, message: String) {

            val context = MyApp.getInstance().getApplicationContext()
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setCancelable(false)
            builder.setPositiveButton(context.getString(android.R.string.ok)) { dialog, which -> dialog.dismiss() }
            val dialog = builder.create()
            if (!dialog.isShowing)
                dialog.show()
        }

        /**
         * Show Default dialog.
         *
         * @param context Application/Activity Context for creating dialog.
         * @param title   Title of dialog
         * @param message Message of dialog
         * @param dialogClickListener Dialog positive and negative listener
         */
        @JvmStatic
        fun dialogYesNo(title: String, message: String, dialogClickListener: IDialogClickListener) {

            val context = MyApp.getInstance().getActivity()
            if (context != null && !context.isFinishing) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                builder.setMessage(message)
                builder.setCancelable(false)
                builder.setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()
                    dialogClickListener.yes()
                }

                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                    dialogClickListener.no()
                }
                val dialog = builder.create()
                if (!dialog.isShowing)
                    dialog.show()
            }
        }


        @JvmStatic
        fun dialog(message: String) {
            dialog(MyApp.getInstance().getApplicationContext().getString(R.string.app_name), message)
        }

        @JvmStatic
        fun error(message: String, vararg args: Any) {
            // On Android there is an extra stack frame that gets the stack trace.
            val stackFrameToPrint = if (SystemUtils.isRunningOnAndroid()) 3 else 2

            val stack = Thread.currentThread().stackTrace
            var errorLocationStr = ""
            if (stack != null && stack.size > stackFrameToPrint + 1) {
                // stack[0] == getStackTrace
                // stack[1] == error
                val callingMethod = stack[stackFrameToPrint]
                errorLocationStr = String.format("MethodError = %s ", callingMethod.toString())
            }

            val messageToLog = errorLocationStr + message
            Timber.e(messageToLog, *args)

            if (!SystemUtils.isInstalledFromMarketPlace()) {
                DebugUtils.showToast(message, args)
            }
        }

        @JvmStatic
        fun logFunc() {
            if (!SystemUtils.isInstalledFromMarketPlace()) {
                // On Android there is an extra stack frame that gets the stack trace.
                val stackFrameToPrint = if (SystemUtils.isRunningOnAndroid()) 3 else 2

                val stack = Thread.currentThread().stackTrace
                if (stack != null && stack.size > stackFrameToPrint + 1) {
                    // stack[0] == getStackTrace
                    // stack[1] == logFunc
                    val callingMethod = stack[stackFrameToPrint]
                    verbose("%s", callingMethod.toString())
                }
            }
        }

        @JvmStatic
        fun logFuncWithMessage(message: String, vararg args: Any) {
            var traceLocationStr = ""
            if (!SystemUtils.isInstalledFromMarketPlace()) {
                // On Android there is an extra stack frame that gets the stack trace.
                val stackFrameToPrint = if (SystemUtils.isRunningOnAndroid()) 3 else 2
                val stack = Thread.currentThread().stackTrace

                if (stack != null && stack.size > stackFrameToPrint + 1) {
                    // stack[0] == getStackTrace
                    // stack[1] == logFuncWithMessage
                    val callingMethod = stack[stackFrameToPrint]
                    traceLocationStr = String.format("%s", callingMethod.toString())
                }
            }
            verbose(traceLocationStr + " " + message, *args)
        }

        @JvmStatic
        fun throwable(t: Throwable?, customMessageTemplate: String, vararg args: Any) {
            val customMessage = String.format(customMessageTemplate, *args)
            var message = "$customMessage Exception Message: "
            message += if (t != null) t.message else "Null Throwable"
            Timber.e(t, message)
            if (!SystemUtils.isInstalledFromMarketPlace()) {
                DebugUtils.showToast(message)
            }
        }

        @JvmStatic
        fun throwable(t: Throwable) {
            throwable(t, "")
        }
    }
}