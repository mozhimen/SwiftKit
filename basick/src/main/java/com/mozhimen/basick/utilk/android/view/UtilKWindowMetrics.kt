package com.mozhimen.basick.utilk.android.view

import android.content.Context
import android.graphics.Rect
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import kotlin.math.max
import kotlin.math.min

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
    fun getCur(context: Context): WindowMetrics =
        UtilKWindowManager.getCurrentWindowMetrics(context)

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getCurBounds(context: Context): Rect =
        getCur(context).bounds

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getCurBoundsWidth(context: Context): Int =
        getCurBounds(context).width()

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getCurBoundsHeight(context: Context): Int =
        getCurBounds(context).height()

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getCurBoundsRatio(context: Context): Float {
        val max: Float = max(getCurBoundsWidth(context), getCurBoundsHeight(context)).toFloat()
        val min: Float = min(getCurBoundsWidth(context), getCurBoundsHeight(context)).toFloat()
        return max / min
    }
}