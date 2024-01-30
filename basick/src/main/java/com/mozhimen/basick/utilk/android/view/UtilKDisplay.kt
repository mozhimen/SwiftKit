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
import com.mozhimen.basick.utilk.android.app.UtilKActivity

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
    fun get(activity: Activity): Display =
        UtilKActivity.getDisplay(activity)

    /**
     * 获取旋转
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_30_11_R)
    fun getRotation(activity: Activity): Int =
        get(activity).rotation

    ///////////////////////////////////////////////////////////

    @JvmStatic
    fun getDef(context: Context): Display =
        UtilKWindowManager.getDefaultDisplay(context)

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
    fun getDefRealSize(context: Context, size: Point) {
        getDef(context).getRealSize(size)
    }

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

    @JvmStatic
    fun isDefOrientationPortrait(context: Context): Boolean =
        getDefOrientation(context) == CActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    @JvmStatic
    fun isDefOrientationLandscape(context: Context): Boolean =
        !isDefOrientationPortrait(context)
}
