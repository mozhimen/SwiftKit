package com.mozhimen.basicsmk.utilmk

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * @ClassName UtilMKWindow
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 13:38
 * @Version 1.0
 */
object UtilMKWindow {
    /**
     * 是否为竖屏
     */
    fun isPortrait(): Boolean {
        val screenResolution = getScreenResolution(UtilMKGlobal.instance.getApp()!!)
        return screenResolution.y > screenResolution.x
    }

    private fun getScreenResolution(context: Context): Point {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val screenResolution = Point()
        display.getSize(screenResolution)
        return screenResolution
    }
}