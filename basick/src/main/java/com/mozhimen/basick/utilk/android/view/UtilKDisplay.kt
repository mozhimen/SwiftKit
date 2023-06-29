package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.android.app.UtilKActivity

/**
 * @ClassName KDisplayUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:27
 * @Version 1.0
 */
object UtilKDisplay {

    @RequiresApi(CVersionCode.V_30_11_R)
    @JvmStatic
    fun get(activity: Activity): Display =
        UtilKActivity.getDisplay(activity)

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
     * @return Int
     */
    @JvmStatic
    fun getDefaultRotation(context: Context): Int =
        getDefault(context).rotation

    /**
     * 获取旋转
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_30_11_R)
    fun getRotation(activity: Activity): Int =
        get(activity).rotation
}
