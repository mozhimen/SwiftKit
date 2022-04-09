package com.mozhimen.basicsmk.utilmk

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.TypedValue
import android.view.WindowManager

/**
 * @ClassName MKDisplayUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:27
 * @Version 1.0
 */
object UtilMKDisplay {
    fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, UtilMKGlobal.instance.getApp()?.resources?.displayMetrics).toInt()
    }

    fun sp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, UtilMKGlobal.instance.getApp()?.resources?.displayMetrics).toInt()
    }

    fun getDisplayWidthInPx(): Int {
        val wm = UtilMKGlobal.instance.getApp()!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun getDisplayHeightInPx(): Int {
        val wm = UtilMKGlobal.instance.getApp()!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

    fun getStatusBarHeightInPx(): Int {
        var statusBarHeight = 0
        val res: Resources = UtilMKGlobal.instance.getApp()!!.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }
}

fun Float.dp2px() = UtilMKDisplay.dp2px(this)

fun Float.sp2px() = UtilMKDisplay.sp2px(this)