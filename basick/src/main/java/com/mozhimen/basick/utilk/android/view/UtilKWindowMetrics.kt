package com.mozhimen.basick.utilk.android.view

import android.content.Context
import android.graphics.Rect
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKWindowMetrics
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/29 18:26
 * @Version 1.0
 */
object UtilKWindowMetrics {
    @JvmStatic
    @RequiresApi(CVersCode.V_30_11_R)
    fun getCurrent(context: Context): WindowMetrics =
        UtilKWindowManager.getCurrentWindowMetrics(context)


    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getCurrentBounds(context: Context): Rect =
        getCurrent(context).bounds

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getCurrentBoundsWidth(context: Context): Int =
        getCurrentBounds(context).width()

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getCurrentBoundsHeight(context: Context): Int =
        getCurrentBounds(context).height()
}