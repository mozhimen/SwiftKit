package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.content.cons.CActivityInfo
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.view.cons.CSurface
import com.mozhimen.basick.lintk.annors.AUsableApi
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import kotlin.math.max
import kotlin.math.min

/**
 * @ClassName UtilKDisplay
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
object UtilKDisplay {

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun get_ofApp(activity: Activity): Display =
        UtilKActivity.getDisplay(activity)

    @JvmStatic
    fun get_ofDef(context: Context): Display =
        UtilKWindowManager.getDefaultDisplay(context)

    ///////////////////////////////////////////////////////////

    //获取旋转
    @JvmStatic
    @RequiresApi(CVersCode.V_30_11_R)
    fun getRotation_ofApp(activity: Activity): Int =
        get_ofApp(activity).rotation

    ///////////////////////////////////////////////////////////

    @JvmStatic
    fun getRealMatrics_ofDef(context: Context, displayMetrics: DisplayMetrics) {
        Display::class.java.getMethod("getRealMetrics", DisplayMetrics::class.java).invoke(get_ofDef(context), displayMetrics)
    }

    @JvmStatic
    fun getMetrics_ofDef(context: Context, displayMetrics: DisplayMetrics) {
        get_ofDef(context).getMetrics(displayMetrics)
    }

    @JvmStatic
    fun getWidth_ofDef(context: Context): Int =
        get_ofDef(context).width

    @JvmStatic
    fun getHeight_ofDef(context: Context): Int =
        get_ofDef(context).height

    @JvmStatic
    fun getRatio_ofDef(context: Context): Float {
        val max = max(getWidth_ofDef(context), getHeight_ofDef(context)).toFloat()
        val min = min(getWidth_ofDef(context), getHeight_ofDef(context)).toFloat()
        return max / min
    }

    @JvmStatic
    fun getSize_ofDef(context: Context, size: Point) {
        get_ofDef(context).getSize(size)
    }

    @JvmStatic
    fun getSize_ofDef(context: Context): Point {
        val size = Point()
        getSize_ofDef(context, size)
        return size
    }

    @JvmStatic
    fun getSizeX_ofDef(context: Context): Int =
        getSize_ofDef(context).x

    @JvmStatic
    fun getSizeY_ofDef(context: Context): Int =
        getSize_ofDef(context).y

    @JvmStatic
    fun getSizeRatio_ofDef(context: Context): Float {
        val max: Float = max(getSizeX_ofDef(context), getSizeY_ofDef(context)).toFloat()
        val min: Float = min(getSizeX_ofDef(context), getSizeY_ofDef(context)).toFloat()
        return max / min
    }

    @JvmStatic
    fun getRealSize_ofDef(context: Context, size: Point) {
        get_ofDef(context).getRealSize(size)
    }

    @JvmStatic
    fun getRealSize_ofDef(context: Context): Point {
        val size = Point()
        getRealSize_ofDef(context, size)
        return size
    }

    @JvmStatic
    fun getRealSizeX_ofDef(context: Context): Int =
        getRealSize_ofDef(context).x

    @JvmStatic
    fun getRealSizeY_ofDef(context: Context): Int =
        getRealSize_ofDef(context).y

    @JvmStatic
    fun getRealSizeRatio_ofDef(context: Context): Float {
        val max: Float = max(getRealSizeX_ofDef(context), getRealSizeY_ofDef(context)).toFloat()
        val min: Float = min(getRealSizeX_ofDef(context), getRealSizeY_ofDef(context)).toFloat()
        return max / min
    }

    @JvmStatic
    @AUsableApi(CVersCode.V_17_42_J1)
    @Deprecated("Deprecated in V_17_42_J1")
    fun getRawWidth_ofDef(context: Context): Int =
        (Display::class.java.getMethod("getRawWidth").invoke(get_ofDef(context)) as Int)

    @JvmStatic
    @AUsableApi(CVersCode.V_17_42_J1)
    @Deprecated("Deprecated in V_17_42_J1")
    fun getRawHeight_ofDef(context: Context): Int =
        (Display::class.java.getMethod("getRawHeight").invoke(get_ofDef(context)) as Int)

    ///////////////////////////////////////////////////////////

    //获取旋转
    @JvmStatic
    fun getRotation_ofDef(context: Context): Int =
        get_ofDef(context).rotation

    @JvmStatic
    fun getOrientation_ofDef(context: Context): Int =
        when (getRotation_ofDef(context)) {
            CSurface.ROTATION_90, CSurface.ROTATION_270 -> CActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else -> CActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

    ///////////////////////////////////////////////////////////

    @JvmStatic
    fun isOrientationPortrait_ofDef(context: Context): Boolean =
        getOrientation_ofDef(context) == CActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    @JvmStatic
    fun isOrientationLandscape_ofDef(context: Context): Boolean =
        !isOrientationPortrait_ofDef(context)
}
