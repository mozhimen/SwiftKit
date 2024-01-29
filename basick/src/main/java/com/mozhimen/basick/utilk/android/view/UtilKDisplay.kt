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
    fun getDefault(context: Context): Display =
        UtilKWindowManager.getDefaultDisplay(context)

    @JvmStatic
    fun getDefaultMetrics(context: Context, displayMetrics: DisplayMetrics) {
        getDefault(context).getMetrics(displayMetrics)
    }

    @JvmStatic
    fun getDefaultWidth(context: Context): Int =
        getDefault(context).width

    @JvmStatic
    fun getDefaultHeight(context: Context): Int =
        getDefault(context).height

    @JvmStatic
    fun getDefaultSize(context: Context, size: Point) {
        getDefault(context).getSize(size)
    }

    @JvmStatic
    fun getDefaultSize(context: Context): Point {
        val size = Point()
        getDefaultSize(context, size)
        return size
    }

    @JvmStatic
    fun getDefaultSizeX(context: Context): Int =
        getDefaultSize(context).x

    @JvmStatic
    fun getDefaultSizeY(context: Context): Int =
        getDefaultSize(context).y

    @JvmStatic
    fun getDefaultRealSize(context: Context, size: Point) {
        getDefault(context).getRealSize(size)
    }

    //获取旋转
    @JvmStatic
    fun getDefaultRotation(context: Context): Int =
        getDefault(context).rotation

    @JvmStatic
    fun getDefaultOrientation(context: Context): Int =
        when (getDefaultRotation(context)) {
            CSurface.ROTATION_90, CSurface.ROTATION_270 -> CActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else -> CActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

    @JvmStatic
    fun isOrientationPortraitOfDefault(context: Context): Boolean =
        getDefaultOrientation(context) == CActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    @JvmStatic
    fun isOrientationLandscapeOfDefault(context: Context): Boolean =
        !isOrientationPortraitOfDefault(context)
}
