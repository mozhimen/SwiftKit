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
    private lateinit var alertDialog: AlertDialog

    fun showLoadingDialog(activity: Activity) {
        alertDialog = AlertDialog.Builder(activity).create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable())
        alertDialog.show()
        alertDialog.setContentView(R.layout.dialog_loading)
    }

    fun dismissLoadingDialog() {
        if (alertDialog != null && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }
}