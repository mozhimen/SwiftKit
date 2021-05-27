package com.mozhimen.swiftmk.helper.os

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import com.mozhimen.swiftmk.R

/**
 * @ClassName AlertDialog
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/24 17:13
 * @Version 1.0
 */
object AlertDialogMK {
    private var alertDialog: AlertDialog? = null

    fun showLoadingDialog(activity: Activity) {
        alertDialog = AlertDialog.Builder(activity).create()
        alertDialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable())
            it.show()
            it.setContentView(R.layout.dialog_loading)
        }
    }

    fun dismissLoadingDialog() {
        alertDialog?.let {
            if(it.isShowing) it.dismiss()
        }
    }
}