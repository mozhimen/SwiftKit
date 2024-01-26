package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import android.view.Surface
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
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
    fun getDefaultRealSize(context: Context, size: Point) {
        getDefault(context).getRealSize(size)
    }

    /**
     * 获取旋转
     */
    @JvmStatic
    fun getDefaultRotation(context: Context): Int =
        getDefault(context).rotation

    /**
     * 获取旋转
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_30_11_R)
    fun getRotation(activity: Activity): Int =
        get(activity).rotation

    @JvmStatic
    fun getOrientation(context: Context): Int {
        val rotation: Int = getDefaultRotation(context)
        var orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        orientation = if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            // 设备的自然方向是纵向
            if (rotation == Surface.ROTATION_0) {
                // 屏幕的实际方向也是纵向
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                // 屏幕的实际方向是横向
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        } else {
            // 设备的自然方向是横向
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                // 屏幕的实际方向也是横向
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                // 屏幕的实际方向是纵向
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
        return orientation
    }
}
