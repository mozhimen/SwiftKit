package com.mozhimen.basick.utilk.view.window

import android.app.Activity
import android.content.Context
import android.view.Display
import android.view.WindowManager
import android.view.WindowMetrics
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.content.activity.UtilKActivity


/**
 * @ClassName UtilKWindowManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:33
 * @Version 1.0
 */
object UtilKWindowManager {

    /**
     * 获取windowManager
     * @return WindowManager
     */
    @JvmStatic
    fun get(context: Context): WindowManager =
        UtilKContext.getWindowManager(context)


    /**
     * 获取window
     * @param activity Activity
     * @return Window
     */
    @JvmStatic
    fun get(activity: Activity): WindowManager =
        UtilKActivity.getWindowManager(activity)

    /**
     * 获取currentWindowMetrics
     * @return WindowMetrics
     */
    @JvmStatic
    fun getCurrentWindowMetrics(context: Context): WindowMetrics {
        return get(context).currentWindowMetrics
    }

    /**
     * 获取currentWindowMetrics
     * @return WindowMetrics
     */
    @JvmStatic
    fun getCurrentWindowMetrics(activity: Activity): WindowMetrics {
        return get(activity).currentWindowMetrics
    }

    /**
     * 获取DefaultDisplay
     * @param activity Activity
     * @return Display
     */
    @JvmStatic
    fun getDefaultDisplay(activity: Activity): Display =
        get(activity).defaultDisplay

    /**
     * 获取DefaultDisplay
     * @return Display
     */
    @JvmStatic
    fun getDefaultDisplay(context: Context): Display =
        get(context).defaultDisplay

    /**
     * 获取旋转
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getRotation(activity: Activity): Int =
        getDefaultDisplay(activity).rotation

    /**
     * 获取旋转
     * @return Int
     */
    @JvmStatic
    fun getRotation(context: Context): Int =
        getDefaultDisplay(context).rotation
}