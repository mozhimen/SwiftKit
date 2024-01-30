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
 * @Date 2021/12/24 14:27
 * @Version 1.0
 */
object UtilKDisplay {

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getApp(activity: Activity): Display =
        UtilKActivity.getDisplay(activity)

    @JvmStatic
    fun getDef(context: Context): Display =
        UtilKWindowManager.getDefaultDisplay(context)

    ///////////////////////////////////////////////////////////

    //获取旋转
    @JvmStatic
    @RequiresApi(CVersCode.V_30_11_R)
    fun getAppRotation(activity: Activity): Int =
        getApp(activity).rotation

    ///////////////////////////////////////////////////////////

    @JvmStatic
    fun getDefMetrics(context: Context, displayMetrics: DisplayMetrics) {
        getDef(context).getMetrics(displayMetrics)
    }

    @JvmStatic
    fun getDefWidth(context: Context): Int =
        getDef(context).width

    @JvmStatic
    fun getDefHeight(context: Context): Int =
        getDef(context).height

    @JvmStatic
    fun getDefRatio(context: Context): Float {
        val max = max(getDefWidth(context), getDefHeight(context)).toFloat()
        val min = min(getDefWidth(context), getDefHeight(context)).toFloat()
        return max / min
    }

    @JvmStatic
    fun getDefSize(context: Context, size: Point) {
        getDef(context).getSize(size)
    }

    @JvmStatic
    fun getDefSize(context: Context): Point {
        val size = Point()
        getDefSize(context, size)
        return size
    }

    @JvmStatic
    fun getDefSizeX(context: Context): Int =
        getDefSize(context).x

    @JvmStatic
    fun getDefSizeY(context: Context): Int =
        getDefSize(context).y

    @JvmStatic
    fun getDefSizeRatio(context: Context): Float {
        val max: Float = max(getDefSizeX(context), getDefSizeY(context)).toFloat()
        val min: Float = min(getDefSizeX(context), getDefSizeY(context)).toFloat()
        return max / min
    }

    @JvmStatic
    fun getDefRealSize(context: Context, size: Point) {
        getDef(context).getRealSize(size)
    }

    @JvmStatic
    fun getDefRealSize(context: Context): Point {
        val size = Point()
        getDefRealSize(context, size)
        return size
    }

    @JvmStatic
    fun getDefRealSizeX(context: Context): Int =
        getDefRealSize(context).x

    @JvmStatic
    fun getDefRealSizeY(context: Context): Int =
        getDefRealSize(context).y

    @JvmStatic
    fun getDefRealSizeRatio(context: Context): Float {
        val max: Float = max(getDefRealSizeX(context), getDefRealSizeY(context)).toFloat()
        val min: Float = min(getDefRealSizeX(context), getDefRealSizeY(context)).toFloat()
        return max / min
    }

    @JvmStatic
    @AUsableApi(CVersCode.V_17_42_J1)
    @Deprecated("Deprecated in V_17_42_J1")
    fun getDefRawWidth(context: Context): Int =
        (Display::class.java.getMethod("getRawWidth").invoke(getDef(context)) as Int)

    @JvmStatic
    @AUsableApi(CVersCode.V_17_42_J1)
    @Deprecated("Deprecated in V_17_42_J1")
    fun getDefRawHeight(context: Context): Int =
        (Display::class.java.getMethod("getRawHeight").invoke(getDef(context)) as Int)

    ///////////////////////////////////////////////////////////

    //获取旋转
    @JvmStatic
    fun getDefRotation(context: Context): Int =
        getDef(context).rotation

    @JvmStatic
    fun getDefOrientation(context: Context): Int =
        when (getDefRotation(context)) {
            CSurface.ROTATION_90, CSurface.ROTATION_270 -> CActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else -> CActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

    ///////////////////////////////////////////////////////////

    @JvmStatic
    fun isDefOrientationPortrait(context: Context): Boolean =
        getDefOrientation(context) == CActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    @JvmStatic
    fun isDefOrientationLandscape(context: Context): Boolean =
        !isDefOrientationPortrait(context)
}
