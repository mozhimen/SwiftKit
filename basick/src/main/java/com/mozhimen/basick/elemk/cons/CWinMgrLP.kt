package com.mozhimen.basick.elemk.cons

import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi

/**
 * @ClassName CWinMgrLP
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 22:40
 * @Version 1.0
 */
object CWinMgrLP {
    const val WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT
    const val MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT
    const val FLAG_NOT_FOCUSABLE = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    const val FLAG_NOT_TOUCHABLE = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    const val FLAG_NOT_TOUCH_MODAL = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
    const val FLAG_TRANSLUCENT_STATUS = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    const val FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN
    const val FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
    const val FLAG_LAYOUT_NO_LIMITS = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

    @RequiresApi(Build.VERSION_CODES.O)
    const val TYPE_APPLICATION_OVERLAY = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
    const val TYPE_SYSTEM_ALERT = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
    const val TYPE_TOAST = WindowManager.LayoutParams.TYPE_TOAST

    const val SOFT_INPUT_ADJUST_RESIZE = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    const val SOFT_INPUT_STATE_UNCHANGED = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
    const val SOFT_INPUT_ADJUST_PAN = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
}