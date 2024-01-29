package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.elemk.android.view.cons.CWindow
import com.mozhimen.basick.elemk.cons.CPackage
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
        window.findViewById(CPackage.ANDROID_R_ID_CONTENT)

    @JvmStatic
    fun <V : View> getContentViewOfWindow(activity: Activity): V =
        getContentViewOfWindow(get(activity))

    @JvmStatic
    fun <V : View> getContentViewOfWindow(window: Window): V =
        window.findViewById(CWindow.ID_ANDROID_CONTENT)

    @JvmStatic
    fun getAttributes(activity: Activity): WindowManager.LayoutParams =
        getAttributes(get(activity))

    @JvmStatic
    fun getAttributes(window: Window): WindowManager.LayoutParams =
        window.attributes

    @JvmStatic
    fun getAttributesFlags(activity: Activity): Int =
        getAttributesFlags(get(activity))

    @JvmStatic
    fun getAttributesFlags(window: Window): Int =
        getAttributes(window).flags

    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isFlagStatusBarTranslucent(activity: Activity): Boolean =
        getAttributesFlags(activity) and CWinMgr.Lpf.TRANSLUCENT_STATUS != 0

    @JvmStatic
    fun isFlagFullScreen(activity: Activity): Boolean =
        getAttributesFlags(activity) and CWinMgr.Lpf.FULLSCREEN != 0

    @JvmStatic
    fun isFullScreen(activity: Activity): Boolean =
        isFlagFullScreen(activity) || !UtilKNavigationBar.isVisible(activity) || !UtilKStatusBar.isVisible(activity)

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyFlagsFullScreen(window: Window) {
        applyFlags(window, CWinMgr.Lpf.FULLSCREEN, CWinMgr.Lpf.FULLSCREEN)
    }

    @JvmStatic
    fun applyFlags(activity: Activity, flags: Int, mask: Int) {
        applyFlags(get(activity), flags, mask)
    }

    @JvmStatic
    fun applyFlags(window: Window, flags: Int, mask: Int) {
        window.setFlags(flags, mask)
    }

    @JvmStatic
    fun applyAttributes(activity: Activity, attributes: WindowManager.LayoutParams) {
        applyAttributes(get(activity), attributes)
    }

    @JvmStatic
    fun applyAttributes(window: Window, attributes: WindowManager.LayoutParams) {
        window.attributes = attributes
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun applyStatusBarColor(activity: Activity, @ColorInt intColor: Int) {
        get(activity).statusBarColor = intColor
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun applyNavigationBarColor(activity: Activity, @ColorInt intColor: Int) {
        get(activity).navigationBarColor = intColor
    }

    @JvmStatic
    fun clearFlags(activity: Activity, flags: Int) {
        get(activity).clearFlags(flags)
    }

    @JvmStatic
    fun addFlags(activity: Activity, flags: Int) {
        get(activity).addFlags(flags)
    }
}

