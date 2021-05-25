package com.mozhimen.swiftmk.helper.os

import android.app.Activity
import android.view.inputmethod.InputMethodManager

/**
 * @ClassName KeyBoard
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/24 10:21
 * @Version 1.0
 */
object KeyBoardMK {
    /**
     * 隐藏键盘
     */
    fun hideKeyBoard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.window.peekDecorView()
        view?.let {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}