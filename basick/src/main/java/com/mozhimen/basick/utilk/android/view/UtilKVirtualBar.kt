package com.mozhimen.basick.utilk.android.view

import android.content.Context
import com.mozhimen.basick.utilk.android.util.UtilKDisplayMetrics
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK

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
        try {
            return UtilKDisplayMetrics.getHeightPixels_ofReal(context) - UtilKDisplay.getHeight_ofDef(context)
        } catch (e: Exception) {
            e.printStackTrace()
            "getHeight Exception ${e.message}".e(TAG)
        }
        return 0
    }
}