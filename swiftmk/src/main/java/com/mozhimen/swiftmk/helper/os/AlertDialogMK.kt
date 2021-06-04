package com.mozhimen.swiftmk.helper.os

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.mozhimen.swiftmk.R

/**
 * @ClassName AlertDialog
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/24 17:13
 * @Version 1.0
 */
object AlertDialogMK {
    private var loadingDialog: AlertDialog? = null

    fun showLoadingDialog(activity: Activity) {
        loadingDialog = AlertDialog.Builder(activity).create()
        loadingDialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable())
            it.show()
            it.setContentView(R.layout.dialog_loading)
        }
    }

    fun dismissLoadingDialog() {
        loadingDialog?.let {
            if (it.isShowing) it.dismiss()
        }
    }


}