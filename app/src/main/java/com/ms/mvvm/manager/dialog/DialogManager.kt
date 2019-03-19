package com.ms.mvvm.manager.dialog

import android.app.Dialog
import com.ms.mvvm.MyApp
import com.ms.mvvm.R
import com.ms.mvvm.interfaces.IApp
import javax.inject.Inject

class DialogManager @Inject constructor(
    var mApp: IApp
) : IDialogManager {

    private var progressDialog: Dialog? = null

    override fun showProgressDialog() {
        val activity  = MyApp.getInstance().getActivity()
        if ((progressDialog == null || !progressDialog!!.isShowing) &&
                (activity != null && !activity.isFinishing)) {
            progressDialog = Dialog(activity, R.style.Base_Theme_AppCompat_Dialog)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.show()
            progressDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            progressDialog!!.setContentView(R.layout.layout_progressdialog)
        }
    }

    override fun dismissProgressDialog() {
        try {
            if (isDialogShowing()) {
                progressDialog!!.dismiss()
                progressDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            progressDialog = null
        }
    }

    override fun isDialogShowing(): Boolean {
        return (progressDialog != null && progressDialog!!.isShowing)
    }

    override fun cleanupModule() {
        dismissProgressDialog()
    }

}