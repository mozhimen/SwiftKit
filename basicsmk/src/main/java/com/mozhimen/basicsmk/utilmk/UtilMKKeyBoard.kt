package com.mozhimen.basicsmk.utilmk

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager

/**
 * @ClassName UtilMKKeyBoard
 * @Description 如果你希望他在7.0生效,你还需设置manifest->android:windowSoftInputMode="stateAlwaysHidden"
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:01
 * @Version 1.0
 */
object UtilMKKeyBoard {
    fun hideKeyBoard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.window.decorView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}