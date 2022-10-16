package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

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
    @JvmStatic
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

    /**
     * 是否隐藏软键盘
     * @param view View 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
     * @param event MotionEvent
     * @return Boolean
     */
    @JvmStatic
    fun isNeedHide(view: View, event: MotionEvent): Boolean {
        if (view is EditText) {
            val l = intArrayOf(0, 0)
            view.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + view.getHeight()
            val right = (left
                    + view.getWidth())
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }
}