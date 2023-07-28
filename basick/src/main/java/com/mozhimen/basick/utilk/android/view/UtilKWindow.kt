package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKWindow
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/27 23:14
 * @Version 1.0
 */
fun <V : View> Window.getContentView(): V =
    UtilKWindow.getContentView(this)

object UtilKWindow : BaseUtilK() {

    @JvmStatic
    fun get(activity: Activity): Window =
        activity.window

    //////////////////////////////////////////////////////////////////
    @JvmStatic
    fun getDecorView(activity: Activity): View =
        getDecorView(get(activity))

    @JvmStatic
    fun getDecorView(window: Window): View =
        window.decorView

    @JvmStatic
    fun getPeekDecorView(activity: Activity): View? =
        getPeekDecorView(get(activity))

    @JvmStatic
    fun getPeekDecorView(window: Window): View? =
        window.peekDecorView()

    @JvmStatic
    fun <V : View> getContentView(activity: Activity): V =
        getContentView(get(activity))

    @JvmStatic
    fun <V : View> getContentView(window: Window): V =
        window.findViewById(android.R.id.content)

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun setAttributes(activity: Activity, attributes: WindowManager.LayoutParams) {
        setAttributes(get(activity), attributes)
    }

    @JvmStatic
    fun setAttributes(window: Window, attributes: WindowManager.LayoutParams) {
        window.attributes = attributes
    }

    @JvmStatic
    fun getAttributes(activity: Activity): WindowManager.LayoutParams =
        getAttributes(get(activity))

    @JvmStatic
    fun getAttributes(window: Window): WindowManager.LayoutParams =
        window.attributes

    @JvmStatic
    fun getFlags(activity: Activity): Int =
        getFlags(get(activity))

    @JvmStatic
    fun getFlags(window: Window): Int =
        getAttributes(window).flags

    @JvmStatic
    fun clearFlags(activity: Activity, flags: Int) {
        get(activity).clearFlags(flags)
    }

    @JvmStatic
    fun addFlags(activity: Activity, flags: Int) {
        get(activity).addFlags(flags)
    }

    @JvmStatic
    fun setFlags(activity: Activity, flags: Int, mask: Int) {
        setFlags(get(activity), flags, mask)
    }

    @JvmStatic
    fun setFlags(window: Window, flags: Int, mask: Int) {
        window.setFlags(flags, mask)
    }

    @JvmStatic
    fun setStatusBarColor(activity: Activity, @ColorInt colorInt: Int) {
        get(activity).statusBarColor = colorInt
    }

    @JvmStatic
    fun setNavigationBarColor(activity: Activity, @ColorInt colorInt: Int) {
        get(activity).navigationBarColor = colorInt
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isFlagStatusBarTranslucent(activity: Activity): Boolean =
        getFlags(activity) and CWinMgr.Lpf.TRANSLUCENT_STATUS != 0

    @JvmStatic
    fun setFlagsFullScreen(window: Window) {
        setFlags(window, CWinMgr.Lpf.FULLSCREEN, CWinMgr.Lpf.FULLSCREEN)
    }

    @JvmStatic
    fun isFullScreenInFlag(activity: Activity): Boolean =
        getFlags(activity) and CWinMgr.Lpf.FULLSCREEN != 0

    /**
     * 是否全屏
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isFullScreenInFlag2(activity: Activity): Boolean =
        getFlags(activity) and CWinMgr.Lpf.FULLSCREEN == CWinMgr.Lpf.FULLSCREEN

    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        isFullScreenInFlag2(activity) || !UtilKNavigationBar.isVisible(activity) || !UtilKStatusBar.isVisible(activity)
}

