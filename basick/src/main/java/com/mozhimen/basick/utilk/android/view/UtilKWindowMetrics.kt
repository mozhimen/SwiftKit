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
    fun get_ofCur(context: Context): WindowMetrics =
        UtilKWindowManager.getCurrentWindowMetrics(context)

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getBounds_ofCur(context: Context): Rect =
        get_ofCur(context).bounds

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getBoundsWidth_ofCur(context: Context): Int =
        getBounds_ofCur(context).width()

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getBoundsHeight_ofCur(context: Context): Int =
        getBounds_ofCur(context).height()

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getBoundsRatio_ofCur(context: Context): Float {
        val max: Float = max(getBoundsWidth_ofCur(context), getBoundsHeight_ofCur(context)).toFloat()
        val min: Float = min(getBoundsWidth_ofCur(context), getBoundsHeight_ofCur(context)).toFloat()
        return max / min
    }
}