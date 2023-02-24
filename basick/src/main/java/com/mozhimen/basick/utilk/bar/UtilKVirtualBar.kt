package com.mozhimen.basick.utilk.bar

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager

/**
 * @ClassName UtilKVirtualBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:44
 * @Version 1.0
 */
object UtilKVirtualBar {
    private val TAG = "UtilKVirtualBar>>>>>"

    /**
     * 获取虚拟功能键的高度
     * @param context Context
     * @return Int
     */
    @JvmStatic
    fun getVirtualBarHeight(context: Context): Int {
        var virtualBarHeight = 0
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        try {
            @SuppressWarnings("rawtypes")
            val clazz = Class.forName("android.view.Display")

            @SuppressWarnings("unchecked")
            val method = clazz.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, displayMetrics)
            virtualBarHeight = displayMetrics.heightPixels - display.height
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "getVirtualBarHeight Exception ${e.message}")
        }
        return virtualBarHeight
    }
}