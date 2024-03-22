package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKContentView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 15:47
 * @Version 1.0
 */
object UtilKContentView : BaseUtilK() {

    @JvmStatic
    fun <V : View> get_ofPac(window: Window): V =
        UtilKWindow.getContentView_ofPackage(window)

    @JvmStatic
    fun <V : View> get_ofPac(activity: Activity): V =
        get_ofPac(activity.window)

    @JvmStatic
    fun <V : View> get_ofWin(window: Window): V =
        UtilKWindow.getContentView_ofWindow(window)

    @JvmStatic
    fun <V : View> get_ofWin(activity: Activity): V =
        get_ofWin(activity.window)

    @JvmStatic
    fun <V : View> get_ofDecor(activity: Activity): V =
        UtilKDecorView.getContentView(activity)

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getAsViewGroup_ofPac(activity: Activity): ViewGroup =
        get_ofPac(activity.window)

    @JvmStatic
    fun getAsViewGroup_ofPac(window: Window): ViewGroup =
        get_ofPac(window)

    @JvmStatic
    fun getTag_ofPac(window: Window, tag: Int): Any? =
        get_ofPac<View>(window).getTag(tag)

    @JvmStatic
    fun getChildAt0_ofPac(window: Window): View? =
        getAsViewGroup_ofPac(window).getChildAt(0)

    @JvmStatic
    fun getChildAt0_ofPac(activity: Activity): View? =
        getChildAt0_ofPac(activity.window)

    @JvmStatic
    fun getWindowVisibleDisplayFrame_ofPac(window: Window): Rect =
        getWindowVisibleDisplayFrame_ofPac(window, Rect())

    @JvmStatic
    fun getWindowVisibleDisplayFrame_ofPac(window: Window, rect: Rect): Rect {
        UtilKView.getWindowVisibleDisplayFrame(get_ofPac(window), rect)
        return rect
    }

    @JvmStatic
    fun getTop_ofPac(activity: Activity): Int =
        get_ofPac<View>(activity).top

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getTop_ofWin(activity: Activity): Int =
        get_ofWin<View>(activity).top
}