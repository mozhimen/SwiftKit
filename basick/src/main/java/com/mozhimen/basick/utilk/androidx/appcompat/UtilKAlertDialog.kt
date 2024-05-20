package com.mozhimen.basick.utilk.androidx.appcompat

import android.app.Activity
import android.app.AlertDialog
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.wrapper.gainString

/**
 * @ClassName UtilKAlertDialog
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/5/19 23:56
 * @Version 1.0
 */
object UtilKAlertDialog {
    @JvmStatic
    fun show(activity: Activity, intResStrMsg: Int, intResStrLabel: Int, block: I_Listener) {
        show(activity, activity.gainString(intResStrMsg), activity.gainString(intResStrLabel), block)
    }

    @JvmStatic
    fun show(activity: Activity, strMsg: String, strLabel: String, block: I_Listener) {
        AlertDialog.Builder(activity)
            .setMessage(strMsg)
            .setPositiveButton(strLabel) { _, _ -> block.invoke() }
            .setCancelable(false)
            .show()
    }
}