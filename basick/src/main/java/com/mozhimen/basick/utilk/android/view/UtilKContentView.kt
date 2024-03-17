package com.mozhimen.basick.utilk.android.view

import android.app.Activity
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
    fun <V : View> getPac(window: Window): V =
        UtilKWindow.getContentView_ofPackage(window)

    @JvmStatic
    fun <V : View> getPac(activity: Activity): V =
        getPac(activity.window)

    @JvmStatic
    fun <V : View> getWin(window: Window): V =
        UtilKWindow.getContentView_ofWindow(window)

    @JvmStatic
    fun <V : View> getWin(activity: Activity): V =
        getWin(activity.window)

    @JvmStatic
    fun getPacAsViewGroup(activity: Activity): ViewGroup =
        getPac(activity.window)

    @JvmStatic
    fun getPacAsViewGroup(window: Window): ViewGroup =
        getPac(window)

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getPacTag(window: Window, tag: Int): Any? =
        getPac<View>(window).getTag(tag)

    @JvmStatic
    fun getPacChild0(window: Window): View? =
        getPacAsViewGroup(window).getChildAt(0)

    @JvmStatic
    fun getPacChild0(activity: Activity): View? =
        getPacChild0(activity.window)
}