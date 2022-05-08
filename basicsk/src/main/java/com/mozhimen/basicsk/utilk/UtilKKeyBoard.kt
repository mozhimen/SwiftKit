package com.mozhimen.basicsk.utilk

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager

/**
 * @ClassName UtilKKeyBoard
 * @Description 如果你希望他在7.0生效,你还需设置manifest->android:windowSoftInputMode="stateAlwaysHidden"
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:01
 * @Version 1.0
 */
object UtilKKeyBoard {
    /**
     * 隐藏键盘
     * @param activity Activity
     */
    fun hide(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.window.peekDecorView() != null || inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}