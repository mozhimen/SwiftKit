package com.mozhimen.basick.utilk.android.view

import android.content.Context
import android.util.DisplayMetrics
import android.view.Display
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName UtilKVirtualBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:44
 * @Version 1.0
 */
object UtilKVirtualBar : IUtilK {

    /**
     * 获取虚拟功能键的高度
     * @param context Context
     * @return Int
     */
    @JvmStatic
    fun getHeight(context: Context): Int {
        var virtualBarHeight = 0
        val displayMetrics = DisplayMetrics()
        try {
            Display::class.java.getMethod("getRealMetrics", DisplayMetrics::class.java).invoke(UtilKDisplay.getDefault(context), displayMetrics)
            virtualBarHeight = displayMetrics.heightPixels - UtilKDisplay.getDefaultWidth(context)
        } catch (e: Exception) {
            e.printStackTrace()
            "getVirtualBarHeight Exception ${e.message}".et(TAG)
        }
        return virtualBarHeight
    }
}