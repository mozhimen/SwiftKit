package com.mozhimen.basick.utilk.android.view

import android.content.Context
import android.util.DisplayMetrics
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et

/**
 * @ClassName UtilKVirtualBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:44
 * @Version 1.0
 */
object UtilKVirtualBar : BaseUtilK() {

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
            @SuppressWarnings("rawtypes")
            val clazz = Class.forName("android.view.Display")

            @SuppressWarnings("unchecked")
            val method = clazz.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(UtilKWindowManager.getDefaultDisplay(context), displayMetrics)
            virtualBarHeight = displayMetrics.heightPixels - UtilKWindowManager.getDefaultDisplayWidth(context)
        } catch (e: Exception) {
            e.printStackTrace()
            "getVirtualBarHeight Exception ${e.message}".et(TAG)
        }
        return virtualBarHeight
    }
}